package com.example.newsfeed.domain.usecase

import com.example.newsfeed.data.model.Results
import com.example.newsfeed.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/*
Use case to save the news articles that we want to view for later.

For each use case, we always reference the repository.
 */
class SaveTheNewsArticle(private val newsRepository: NewsRepository) {

    suspend fun execute(results: Results) {
        withContext(Dispatchers.IO) {
            newsRepository.saveNews(results)
        }
    }
}