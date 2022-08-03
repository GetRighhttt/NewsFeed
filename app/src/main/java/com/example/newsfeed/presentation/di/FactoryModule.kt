package com.example.newsfeed.presentation.di

import android.app.Application
import com.example.newsfeed.domain.usecase.*
import com.example.newsfeed.presentation.viewmodel.factory.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
Class for factory dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {
    /*
    Method to provide Factory with its dependencies.
     */
    @Singleton
    @Provides
    fun provideNewsViewModelFactory(
        app: Application,
        getNewsHeadlines: GetNewsHeadlines,
        getSearchedNewsHeadlines: GetSearchedNewsHeadlines,
        saveNewsUseCase: SaveTheNewsArticle,
        getSavedNews: GetSavedNews,
        deleteSavedNewsArticle: DeleteSavedNews
    ): NewsViewModelFactory {
        return NewsViewModelFactory(
            getNewsHeadlines,
            app,
            getSearchedNewsHeadlines,
            saveNewsUseCase,
            getSavedNews,
            deleteSavedNewsArticle
        )
    }
}