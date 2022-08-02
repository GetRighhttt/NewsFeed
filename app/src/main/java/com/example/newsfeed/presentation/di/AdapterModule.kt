package com.example.newsfeed.presentation.di

import com.example.newsfeed.presentation.view.fragments.news.NewsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
Module to provide the adapter with its dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {

    @Singleton
    @Provides
    fun providesAdapter(): NewsAdapter {
        return NewsAdapter()
    }
}