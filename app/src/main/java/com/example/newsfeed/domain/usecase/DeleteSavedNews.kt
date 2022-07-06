package com.example.newsfeed.domain.usecase

import com.example.newsfeed.data.model.Article
import com.example.newsfeed.domain.repository.NewsRepository

/*
This use case serves the purpose of deleting saved news articles.
 */
class DeleteSavedNews(private val newsRepository: NewsRepository) {

    suspend fun execute(article: Article) {
        return newsRepository.deleteNews(article)
    }
}