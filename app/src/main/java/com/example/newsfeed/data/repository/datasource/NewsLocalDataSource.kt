package com.example.newsfeed.data.repository.datasource

import com.example.newsfeed.data.model.Article

interface NewsLocalDataSource {
    suspend fun saveArticleToDB(article: Article)
}