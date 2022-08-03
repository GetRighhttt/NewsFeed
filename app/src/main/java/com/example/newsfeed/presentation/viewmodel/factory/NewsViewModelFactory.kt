package com.example.newsfeed.presentation.viewmodel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsfeed.domain.usecase.*
import com.example.newsfeed.presentation.viewmodel.NewsViewModel

/*
Factory class for our view model.
 */
class NewsViewModelFactory(
    private val getNewsHeadlines: GetNewsHeadlines,
    private val app: Application,
    private val getSearchedNewsHeadlines: GetSearchedNewsHeadlines,
    private val saveNewsUseCase: SaveTheNewsArticle,
    private val getSavedNews: GetSavedNews,
    private val deleteSavedNewsArticle: DeleteSavedNews
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(
            getNewsHeadlines,
            app,
            getSearchedNewsHeadlines,
            saveNewsUseCase,
            getSavedNews,
            deleteSavedNewsArticle
        ) as T
    }
}