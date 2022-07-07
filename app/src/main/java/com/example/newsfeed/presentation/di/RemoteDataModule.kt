package com.example.newsfeed.presentation.di

import com.example.newsfeed.data.api.NewsApiService
import com.example.newsfeed.data.repository.datasource.NewsRemoteDataSource
import com.example.newsfeed.data.repository.datasourceimpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    /*
    Method to provide the remote data source with its dependencies.

    It takes our api service as a parameter because its a parameter in the
    actual data source class.

    We always use the implementation class.
     */
    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(
        newsApiService: NewsApiService
    ): NewsRemoteDataSource {
        return NewsRemoteDataSourceImpl(newsApiService)
    }
}