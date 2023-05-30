package com.example.newsfeed.data.repository.datasourceimpl

import com.example.newsfeed.data.db.DAO
import com.example.newsfeed.data.model.Results
import com.example.newsfeed.data.repository.datasource.NewsLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class NewLocalDataSourceImpl(private val articleDao: DAO): NewsLocalDataSource {
    override suspend fun saveArticleToDB(results: Results) {
        withContext(Dispatchers.IO) {
            articleDao.insert(results)
        }
    }

    override fun getSavedData(): Flow<List<Results>> {
        return articleDao.getAllArticles()
    }

    override suspend fun deleteSavedNewsArticles(results: Results) {
        withContext(Dispatchers.IO) {
            articleDao.deleteSavedNewsArticles(results)
        }
    }


}