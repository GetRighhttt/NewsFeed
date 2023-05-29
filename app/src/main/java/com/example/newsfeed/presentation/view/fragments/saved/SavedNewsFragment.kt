package com.example.newsfeed.presentation.view.fragments.saved

import android.content.ClipData
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeed.R
import com.example.newsfeed.databinding.FragmentSavedNewsBinding
import com.example.newsfeed.presentation.view.MainActivity
import com.example.newsfeed.presentation.view.fragments.news.NewsAdapter
import com.example.newsfeed.presentation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class SavedNewsFragment : Fragment() {

    private lateinit var binding: FragmentSavedNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var savedAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_news, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedNewsBinding.bind(view)

        viewModel = (activity as MainActivity).viewModel
        savedAdapter = (activity as MainActivity).newsAdapter
        savedAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_article", it)
            }
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_detailsFragment,
                bundle
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
        // Injecting as singleton -> savedAdapter = NewsAdapter()
        binding.rvSavedNews.apply {
            adapter = savedAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    /*
    Method to observe live data from view model and submit the list.
     */
    private fun observeLiveData() {
        viewModel.getSavedNews().observe(viewLifecycleOwner, Observer {
            savedAdapter.differ.submitList(it)
        })
    }

    private fun createItemCallBack() {
        /*
        We want to allow the user to delete the article item by swiping. To do that, we must:

        Implement an item touch helper callback method with a simple call back.

        This below is how we allow for swipe interactions.
         */
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            /*
            This method below is how we determine actions when the user has swiped on an
            item.

            We create a swipe mechanic to delete the article.

            We also create an Undo action to save the article if the user wants to undo the
            deletion.
             */
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = savedAdapter.differ.currentList[position]
                lifecycleScope.launch {
                    viewModel.deleteSavedNewsArticle(article)
                }
                view?.let {
                    Snackbar.make(it, "Article Deleted", Snackbar.LENGTH_LONG)
                        .apply {
                            setAction("Undo") {
                                lifecycleScope.launch {
                                    viewModel.saveArticle(article)
                                }
                            }
                        }
                        .show()
                }
            }

        }

        // attach ItemTouchHelper to the recycler view.
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvSavedNews)
        }
    }
}