package com.example.newsfeed.presentation.di

import com.example.newsfeed.data.db.DAO
import com.example.newsfeed.data.repository.datasource.NewsLocalDataSource
import com.example.newsfeed.data.repository.datasourceimpl.NewLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NewLocalDataSourceModule {

    @Provides
    @Singleton
    fun provideLocalDataSource(dao: DAO): NewsLocalDataSource {
        return NewLocalDataSourceImpl(dao)
    }

}