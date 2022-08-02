package com.example.newsfeed.presentation.di

import com.example.newsfeed.data.repository.datasource.NewsLocalDataSource
import com.example.newsfeed.data.repository.datasource.NewsRemoteDataSource
import com.example.newsfeed.data.repository.repositoryimpl.NewsRepositoryImpl
import com.example.newsfeed.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
Class to provide dependencies to our repository modules.
 */

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    /*
    Provide our news repository with its dependencies.
     */
    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: NewsRemoteDataSource,
        newsLocalDataSource: NewsLocalDataSource
    ): NewsRepository {
        return NewsRepositoryImpl(remoteDataSource, newsLocalDataSource)
    }
}