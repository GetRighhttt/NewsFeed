package com.example.newsfeed.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.newsfeed.data.model.Results
import com.example.newsfeed.data.model.NewsResponse
import com.example.newsfeed.data.util.Resource
import com.example.newsfeed.domain.usecase.*
import com.example.newsfeed.util.PermissionsHandler.isNetworkAvailable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    private val getSavedNews: GetSavedNews,
    private val deleteSavedNewsArticle: DeleteSavedNews
) : AndroidViewModel(app) {
    private val _newsHeadlines = MutableLiveData<Resource<NewsResponse>>()
    val newsHeadlines: MutableLiveData<Resource<NewsResponse>> = _newsHeadlines

    /**
    Use coroutines to launch a job to get the news headlines in a background thread.

    If loading or in an error state, display a message.

    To get the response, we need an instance of getNewsHeadLines from the UseCase.
     */
    fun getNewsHeadLines() = viewModelScope.launch(Dispatchers.IO) {
        _newsHeadlines.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val apiResult = getNewsHeadlines.execute()
                _newsHeadlines.postValue(apiResult)
            } else {
                _newsHeadlines.postValue(Resource.Error("Internet is not available."))
            }
        } catch (e: Exception) {
            _newsHeadlines.postValue(Resource.Error(e.message.toString()))
        }
    }
    /**
     * Get Search results from Use Case class.
     *
     * Same as above, checking network availability, and posting the result using
     * mutable live data.
     */
    private val _searchedNews = MutableLiveData<Resource<NewsResponse>>()
    val searchedNews: MutableLiveData<Resource<NewsResponse>> = _searchedNews

    fun searchNews(
        query: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        _searchedNews.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val apiResult = getSearchedNewsHeadlines.execute(
                    query
                )
                _searchedNews.postValue(apiResult)
            } else {
                _searchedNews.postValue(Resource.Error("Internet is not available."))
            }
        } catch (e: Exception) {
            _searchedNews.postValue(Resource.Error(e.message.toString()))
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
    fun getSavedNews() = liveData(Dispatchers.IO) {
        getSavedNews.execute().collect { results ->
            emit(results)
        }
    }

    /*
    Method to delete the saved news article.
     */
    fun deleteSavedNewsArticle(results: Results) = viewModelScope.launch(Dispatchers.IO) {
        deleteSavedNewsArticle.execute(results)
    }
}