package com.example.newsfeed.domain.usecase

import com.example.newsfeed.data.model.Results
import com.example.newsfeed.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/*
This use case serves the purpose of deleting saved news articles.
 */
class DeleteSavedNews(private val newsRepository: NewsRepository) {

    suspend fun execute(results: Results) {
        withContext(Dispatchers.IO) {
            newsRepository.deleteSavedNewsArticles(results)
        }
    }
}