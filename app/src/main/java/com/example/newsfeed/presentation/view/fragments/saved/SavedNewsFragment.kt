package com.example.newsfeed.presentation.view.fragments.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeed.R
import com.example.newsfeed.databinding.FragmentSavedNewsBinding
import com.example.newsfeed.presentation.model.toArticleArgs
import com.example.newsfeed.presentation.view.MainActivity
import com.example.newsfeed.presentation.view.fragments.news.NewsAdapter
import com.example.newsfeed.presentation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar


class SavedNewsFragment : Fragment() {

    private var _binding: FragmentSavedNewsBinding? = null
    private val binding get() = checkNotNull(_binding)
    private lateinit var viewModel: NewsViewModel
    private val savedAdapter = NewsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedNewsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (requireActivity() as MainActivity).viewModel
        savedAdapter.setOnItemClickListener {
            findNavController().navigate(
                SavedNewsFragmentDirections.actionSavedNewsFragmentToDetailsFragment(
                    it.toArticleArgs()
                )
            )
        }
        initRecyclerView() // method to initialize recycler view
        observeLiveData() // method to observe Live Data from view model
        createItemCallBack() // method for swipe mechanics to delete article
    }

    /*
  Method to initialize recycler view with apply method.
   */
    private fun initRecyclerView() {
        binding.rvSavedNews.apply {
            adapter = savedAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    /*
    Method to observe live data from view model and submit the list.
     */
    private fun observeLiveData() {
        viewModel.savedNews.observe(viewLifecycleOwner) {
            savedAdapter.differ.submitList(it)
        }
    }

    private fun createItemCallBack() {
        /*
        We want to allow the user to delete the article item by swiping. To do that, we must:

        Implement an item touch helper callback method with a simple call back.

        This below is how we allow for swipe interactions.
         */
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            /*
            This method below is how we determine actions when the user has swiped on an
            item.

            We create a swipe mechanic to delete the article.

            We also create an Undo action to save the article if the user wants to undo the
            deletion.
             */
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                if (position == RecyclerView.NO_POSITION) return
                val article = savedAdapter.differ.currentList[position]
                viewModel.deleteSavedNewsArticle(article)
                Snackbar.make(
                    binding.root,
                    R.string.article_deleted,
                    Snackbar.LENGTH_LONG
                )
                    .apply {
                        setAction(R.string.undo) {
                            viewModel.saveArticle(article)
                        }
                    }
                    .show()
            }

        }

        // attach ItemTouchHelper to the recycler view.
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvSavedNews)
        }
    }

    override fun onDestroyView() {
        binding.rvSavedNews.adapter = null
        savedAdapter.setOnItemClickListener(null)
        _binding = null
        super.onDestroyView()
    }
}
