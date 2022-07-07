package com.example.newsfeed.presentation.viewmodel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsfeed.domain.usecase.GetNewsHeadlines
import com.example.newsfeed.presentation.viewmodel.NewsViewModel

/*
Factory class for our view model.
 */
class NewsViewModelFactory(
    private val getNewsHeadlines: GetNewsHeadlines,
    private val app: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(app, getNewsHeadlines) as T
    }
}