package com.example.newsfeed.data.repository.datasource

import com.example.newsfeed.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {
    suspend fun saveArticleToDB(article: Article)

    fun getSavedData(): Flow<List<Article>>

    suspend fun deleteSavedNewsArticles(article: Article)
}