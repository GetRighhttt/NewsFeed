package com.example.newsfeed.data.repository.datasourceimpl

import com.example.newsfeed.data.db.DAO
import com.example.newsfeed.data.model.Results
import com.example.newsfeed.data.repository.datasource.NewsLocalDataSource
import kotlinx.coroutines.flow.Flow

class NewLocalDataSourceImpl(private val articleDao: DAO): NewsLocalDataSource {
    override suspend fun saveArticleToDB(results: Results) {
        articleDao.insert(results)
    }

    override fun getSavedData(): Flow<List<Results>> {
        return articleDao.getAllArticles()
    }

    override suspend fun deleteSavedNewsArticles(results: Results) {
        return articleDao.deleteSavedNewsArticles(results)
    }


}