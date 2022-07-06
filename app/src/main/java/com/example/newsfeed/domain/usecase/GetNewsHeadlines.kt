package com.example.newsfeed.domain.usecase

import com.example.newsfeed.data.model.NewsResponse
import com.example.newsfeed.data.util.Resource
import com.example.newsfeed.domain.repository.NewsRepository

/*
Use Case to get the news head lines.

For each use case, we always reference the repository.
 */
class GetNewsHeadlines(private val newsRepository: NewsRepository) {
    suspend fun execute(): Resource<NewsResponse> {
        return newsRepository.getNewsHeadlines()
    }
}
