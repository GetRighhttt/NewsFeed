package com.example.newsfeed.domain.usecase

import com.example.newsfeed.data.model.Article
import com.example.newsfeed.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

/*
Use case to get the saved news articles, and we do not use suspend because it returns a flow.
 */
class GetSavedNews(private val newsRepository: NewsRepository) {

    fun execute(): Flow<List<Article>> {
        return newsRepository.getSavedNews()
    }
}