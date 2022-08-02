package com.example.newsfeed.presentation.di

import com.example.newsfeed.domain.repository.NewsRepository
import com.example.newsfeed.domain.usecase.GetNewsHeadlines
import com.example.newsfeed.domain.usecase.GetSearchedNewsHeadlines
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
Class to provide the use cases with dependencies.
 */

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    /*
    Method to return a dependency instance of use case.
     */
    @Singleton
    @Provides
    fun provideGetNewsLinesUseCase(newsRepository: NewsRepository
    ): GetNewsHeadlines {
        return GetNewsHeadlines(newsRepository)
    }

    @Singleton
    @Provides
    fun provideGetSearchedNewsLinesUseCase(newsRepository: NewsRepository
    ): GetSearchedNewsHeadlines {
        return GetSearchedNewsHeadlines(newsRepository)
    }
}