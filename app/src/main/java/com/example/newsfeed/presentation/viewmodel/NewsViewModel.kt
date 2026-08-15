package com.example.newsfeed.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsfeed.R
import com.example.newsfeed.data.model.NewsResponse
import com.example.newsfeed.data.model.Results
import com.example.newsfeed.data.util.Resource
import com.example.newsfeed.domain.usecase.DeleteSavedNews
import com.example.newsfeed.domain.usecase.GetNewsHeadlines
import com.example.newsfeed.domain.usecase.GetSavedNews
import com.example.newsfeed.domain.usecase.GetSearchedNewsHeadlines
import com.example.newsfeed.domain.usecase.SaveTheNewsArticle
import com.example.newsfeed.util.PermissionsHandler.isNetworkAvailable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
View Model class where we demonstrate how to properly incorporate state components into our
view model layer. Have to extend AndroidViewModel in order to pass our application context for
state of the internet purposes.
 */
@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsHeadlines: GetNewsHeadlines,
    private val app: Application,
    private val getSearchedNewsHeadlines: GetSearchedNewsHeadlines,
    private val saveNewsUseCase: SaveTheNewsArticle,
    getSavedNews: GetSavedNews,
    private val deleteSavedNewsArticle: DeleteSavedNews
) : AndroidViewModel(app) {
    private val _newsHeadlines = MutableLiveData<Resource<NewsResponse>>()
    val newsHeadlines: LiveData<Resource<NewsResponse>> = _newsHeadlines
    private var headlinesJob: Job? = null

    /**
    Use coroutines to launch a job to get the news headlines in a background thread.

    If loading or in an error state, display a message.

    To get the response, we need an instance of getNewsHeadLines from the UseCase.
     */
    fun getNewsHeadLines() {
        if (headlinesJob?.isActive == true) return
        headlinesJob = viewModelScope.launch(Dispatchers.IO) {
            _newsHeadlines.postValue(Resource.Loading())
            try {
                if (isNetworkAvailable(app)) {
                    _newsHeadlines.postValue(getNewsHeadlines.execute())
                } else {
                    _newsHeadlines.postValue(Resource.Error(app.getString(R.string.no_internet)))
                }
            } catch (error: CancellationException) {
                throw error
            } catch (error: Exception) {
                _newsHeadlines.postValue(
                    Resource.Error(error.localizedMessage ?: app.getString(R.string.unexpected_error))
                )
            }
        }
    }
    /**
     * Get Search results from Use Case class.
     *
     * Same as above, checking network availability, and posting the result using
     * mutable live data.
     */
    private val _searchedNews = MutableLiveData<Resource<NewsResponse>>()
    val searchedNews: LiveData<Resource<NewsResponse>> = _searchedNews
    private var searchJob: Job? = null

    fun searchNews(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            _searchedNews.postValue(Resource.Loading())
            try {
                if (isNetworkAvailable(app)) {
                    _searchedNews.postValue(getSearchedNewsHeadlines.execute(query))
                } else {
                    _searchedNews.postValue(Resource.Error(app.getString(R.string.no_internet)))
                }
            } catch (error: CancellationException) {
                throw error
            } catch (error: Exception) {
                _searchedNews.postValue(
                    Resource.Error(
                        error.localizedMessage ?: app.getString(R.string.unexpected_error)
                    )
                )
            }
        }
    }

    /**
     * Local data source impl.
     */
    fun saveArticle(results: Results) = viewModelScope.launch(Dispatchers.IO) {
        saveNewsUseCase.execute(results)
    }

    /*
    get the saved news article

    Code to get the flow as a query and convert it to live data.
     */
    val savedNews: LiveData<List<Results>> = getSavedNews.execute().asLiveData()

    /*
    Method to delete the saved news article.
     */
    fun deleteSavedNewsArticle(results: Results) = viewModelScope.launch(Dispatchers.IO) {
        deleteSavedNewsArticle.execute(results)
    }
}
