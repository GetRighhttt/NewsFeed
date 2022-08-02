package com.example.newsfeed.data.repository.datasourceimpl

import com.example.newsfeed.data.db.DAO
import com.example.newsfeed.data.model.Article
import com.example.newsfeed.data.repository.datasource.NewsLocalDataSource

class NewLocalDataSourceImpl(private val articleDao: DAO): NewsLocalDataSource {
    override suspend fun saveArticleToDB(article: Article) {
        articleDao.insert(article)
    }
}