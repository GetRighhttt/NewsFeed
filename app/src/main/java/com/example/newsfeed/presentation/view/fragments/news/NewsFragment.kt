package com.example.newsfeed.presentation.view.fragments.news

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsfeed.R
import com.example.newsfeed.data.util.Resource
import com.example.newsfeed.databinding.FragmentNewsBinding
import com.example.newsfeed.presentation.model.toArticleArgs
import com.example.newsfeed.presentation.view.MainActivity
import com.example.newsfeed.presentation.viewmodel.NewsViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/*
We will show an example of paging here, DI, and DiffUtil usage from the adapter.
 */

class NewsFragment : Fragment() {
    /*
    Create our reference variables.
     */
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val newsAdapter = NewsAdapter()
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (requireActivity() as MainActivity).viewModel
        newsAdapter.setOnItemClickListener {
            findNavController().navigate(
                NewsFragmentDirections.actionNewsFragmentToDetailsFragment(it.toArticleArgs())
            )
        }
        initRecyclerView()
        displayNewsList()
        observeSearchedNews()
        setSearchView()
    }

    /*
    Method to view News list using the use case methods we created in our view model.

    And to check and see if we are at the last page so we can implement paging into
    this method to load more pages.
     */
    private fun displayNewsList() {
        if (viewModel.newsHeadlines.value == null) {
            viewModel.getNewsHeadLines()
        }
        viewModel.newsHeadlines.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.results?.toList() ?: emptyList())
                        if (it.results?.isEmpty() == true) {
                            context?.let { currentContext ->
                                buildMaterialDialog(
                                    currentContext,
                                    getString(R.string.empty_news_title),
                                    getString(R.string.empty_news_message)
                                )
                            }
                        }
                    }
                }

                is Resource.Loading -> {
                    displayProgressBar()
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        buildMaterialDialog(
                            requireContext(),
                            getString(R.string.error_title),
                            it
                        )
                    }
                }
            }
        }
    }

    private fun buildMaterialDialog(
        context: Context,
        title: String,
        message: String
    ) {
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    /*
    Method to initialize recycler view with apply method.

    And also apply an on scroll listen to listen for when we are
    scrolling the recycler view.
     */
    private fun initRecyclerView() {
        binding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
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

    /**
     * Implement function to get search query!
     *
     * Must implement the two members inside the setSearchView method.
     */
    private fun setSearchView() {
        binding.searchViewNews.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    val query = p0?.trim().orEmpty()
                    if (query.isNotEmpty()) {
                        binding.rvNews.smoothScrollToPosition(0)
                        viewModel.searchNews(query)
                    }
                    clearFocus()
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return true
                }
            })
        }
    }

    /*
    Method to display the searched news.
     */
    private fun observeSearchedNews() {
        viewModel.searchedNews.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.results?.toList() ?: emptyList())
                    }
                }

                is Resource.Loading -> {
                    displayProgressBar()
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.search_error, it),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        binding.rvNews.adapter = null
        newsAdapter.setOnItemClickListener(null)
        _binding = null
        super.onDestroyView()
    }

}
