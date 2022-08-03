package com.example.newsfeed.data.repository.datasourceimpl

import com.example.newsfeed.data.db.DAO
import com.example.newsfeed.data.model.Article
import com.example.newsfeed.data.repository.datasource.NewsLocalDataSource
import kotlinx.coroutines.flow.Flow

class NewLocalDataSourceImpl(private val articleDao: DAO): NewsLocalDataSource {
    override suspend fun saveArticleToDB(article: Article) {
        articleDao.insert(article)
    }

    override fun getSavedData(): Flow<List<Article>> {
        return articleDao.getAllArticles()
    }

    override suspend fun deleteSavedNewsArticles(article: Article) {
        return articleDao.deleteSavedNewsArticles(article)
    }


}