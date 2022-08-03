package com.example.newsfeed.presentation.view.fragments.saved

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsfeed.R
import com.example.newsfeed.databinding.FragmentSavedNewsBinding
import com.example.newsfeed.presentation.view.MainActivity
import com.example.newsfeed.presentation.view.fragments.news.NewsAdapter
import com.example.newsfeed.presentation.viewmodel.NewsViewModel


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
                R.id.action_newsFragment_to_detailsFragment,
                bundle
            )
        }
        initRecyclerView() // method to initialize recycler view
        observeLiveData() // method to observe Live Data from viewmodel
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
}