package com.example.newsfeed.presentation.view.fragments.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeed.R
import com.example.newsfeed.data.util.Resource
import com.example.newsfeed.databinding.FragmentNewsBinding
import com.example.newsfeed.presentation.view.MainActivity
import com.example.newsfeed.presentation.viewmodel.NewsViewModel


class NewsFragment : Fragment() {
    /*
    Create our reference variables.
     */
    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentNewsBinding
    private lateinit var newsAdapter: NewsAdapter

    /*
    News List arguments we are passing in.
     */
    private var country: String = "us"
    private var page: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    /*
    Get the ViewModel & adapter instance we constructed inside the main activity.

    onViewCreated called immediately after all the views have been created.
    That's why we are using that to get the ViewModel instance. It's safer.

    Then we use our method to initialize the recycler view.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter
        initRecyclerView()
        displayNewsList()
    }

    /*
    Method to view News list using the use case methods we created in our view model.
     */
    private fun displayNewsList() {
        viewModel.getNewsHeadLines(country, page)
        viewModel.newsHeadlines.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles.toList())
                    }
                }
                is Resource.Loading -> {
                    displayProgressBar()
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(
                            activity,
                            "There was an Error loading $it.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        })
    }

    /*
    Method to initialize recycler view with apply method.
     */
    private fun initRecyclerView() {
        // Can be injected as singleton - newsAdapter = NewsAdapter()
        binding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    /*
    Methods to view and hide progress bar.
     */
    private fun displayProgressBar() {
        binding.progressBar.apply {
            visibility = View.VISIBLE
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.apply {
            visibility = View.GONE
        }
    }

}