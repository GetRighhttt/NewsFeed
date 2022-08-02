package com.example.newsfeed.presentation.view.fragments.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.SearchView
import android.widget.Toast
import androidx.compose.runtime.snapshots.Snapshot.Companion.observe
import androidx.core.view.size
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeed.R
import com.example.newsfeed.data.util.Resource
import com.example.newsfeed.databinding.FragmentNewsBinding
import com.example.newsfeed.presentation.view.MainActivity
import com.example.newsfeed.presentation.viewmodel.NewsViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/*
We will show an example of paging here, DI, and DiffUtil usage from the adapter.
 */

class NewsFragment : Fragment() {
    /*
    Create our reference variables.
     */
    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentNewsBinding
    private lateinit var newsAdapter: NewsAdapter

    /*
    Define a paging variable.
     */
    private var isScrolling = false // by default false

    /*
    News List arguments we are passing in.
     */
    private var country: String = "us"
    private var page: Int = 1
    private var searchQuery: String = "q"

    /*
    Define a variable to check if data is loading.
     */
    private var isLoading = false

    /*
    Variable to check for the last page.
     */
    private var isAtTheLastPage = false

    /*
    Variable to check if at the last page with Modulus operator when showing the list.
     */
    private var pages = 0

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
    It's safer to avoid
    unexpectd errors as a result of partially created views.

    Then we use our method to initialize the recycler view.

    This is how we are going to pass our webview details to display the details
    of our news article in our Details Fragment.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel // get viewModel from Main
        newsAdapter = (activity as MainActivity).newsAdapter // get adapter from Main
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_article", it)
            }
            findNavController().navigate(
                R.id.action_newsFragment_to_detailsFragment,
                bundle
            )
        }
        initRecyclerView() // method to initialize recycler view
        displayNewsList() // method to display the list depending on the state
        setSearchView() // sets the searchview
    }

    /*
    Method to view News list using the use case methods we created in our view model.

    And to check and see if we are at the last page so we can implement paging into
    this method to load more pages.
     */
    private fun displayNewsList() {
        viewModel.getNewsHeadLines(country, page)
        viewModel.newsHeadlines.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles.toList())
                        /*
                        Determininig if at the last page with the page size being 20
                         */
                        if (it.totalResults % 20 == 0) {
                            val pages = it.totalResults / 20 // check if last page
                        } else {
                            pages = it.totalResults / 20 + 1
                        }
                        isAtTheLastPage = page == pages
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

    And also apply an on scroll listen to listen for when we are
    scrolling the recycler view.
     */
    private fun initRecyclerView() {
        // Injecting as singleton -> newsAdapter = NewsAdapter()
        binding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@NewsFragment.onScrollListener)
        }
    }

    /*
    Methods to view and hide progress bar.
     */
    private fun displayProgressBar() {
        isLoading = true
        binding.progressBar.apply {
            visibility = View.VISIBLE
        }
    }

    private fun hideProgressBar() {
        isLoading = false
        binding.progressBar.apply {
            visibility = View.GONE
        }
    }

    /*
    Override this method for manual paging example.

    Paging - fetching data from api to load more pages.
     */
    private val onScrollListener = object : RecyclerView.OnScrollListener() {

        /*
        Method to define what happens when the scroll state is changed.
         */
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        /*
        Method to defnie what happens when we are scrolling, and to determine when we should
        be paging.
         */
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            binding.rvNews.apply {
                val layoutManager = layoutManager as LinearLayoutManager
                val sizeOfCurrentList = layoutManager.itemCount
                val visibleItems = layoutManager.childCount
                val positionOfStartingItem = layoutManager.findFirstVisibleItemPosition()

                val hasReachedToEnd = positionOfStartingItem + visibleItems >= sizeOfCurrentList
                val shouldPaginate = !isLoading && !isAtTheLastPage
                        && hasReachedToEnd && isScrolling
                if (shouldPaginate) {
                    pages++
                    viewModel.getNewsHeadLines(country, page)
                    isScrolling = false
                }
            }
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
                /*
                invoked when user types search and presses enter.

                Listens for text to be entered.

                need to write code to invoke the viewmodel's searchnews()
                 */
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    viewModel.searchNews("us", p0.toString(), page)
                    displaySearchedNews()
                    return false
                }

                /*
                invoked for each exchange in the searchview every time we type or
                remove text, things we write will be invoked.

                called when the text changes in the query.

                should give the user time to input text. we will use the MainScope().launch{}
                since it is speficially designed for UI interactions with coroutines.
                 */
                override fun onQueryTextChange(p0: String?): Boolean {
                    MainScope().launch {
                        delay(2000)
                        viewModel.searchNews("us", p0.toString(), page)
                        displaySearchedNews()
                    }
                    return false
                }

            })

            /*
            code to reset list if user decides to click on close button.
             */
            binding.searchViewNews.apply {
                setOnCloseListener(object : SearchView.OnCloseListener {
                    override fun onClose(): Boolean {
                        initRecyclerView()
                        displayNewsList()
                        return false
                    }

                })
            }
        }
    }

    /*
    Method to display the searched news.
     */
    fun displaySearchedNews() {
        viewModel.searchNews(country, searchQuery, page)
        viewModel.searchedNews.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles.toList())
                        /*
                        Determininig if at the last page with the page size being 20
                         */
                        if (it.totalResults % 20 == 0) {
                            val pages = it.totalResults / 20 // check if last page
                        } else {
                            pages = it.totalResults / 20 + 1
                        }
                        isAtTheLastPage = page == pages
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

}