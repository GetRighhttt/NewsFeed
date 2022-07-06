package com.example.newsfeed.domain.usecase

import com.example.newsfeed.data.model.Article
import com.example.newsfeed.domain.repository.NewsRepository

/*
Use case to save the news articles that we want to view for later.

For each use case, we always reference the repository.
 */
class SaveNews(private val newsRepository: NewsRepository) {

    suspend fun execute(article: Article) {
        return newsRepository.saveNews(article)
    }
}