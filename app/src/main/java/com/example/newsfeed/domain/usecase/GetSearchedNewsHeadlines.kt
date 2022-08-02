package com.example.newsfeed.domain.usecase

import com.example.newsfeed.data.model.NewsResponse
import com.example.newsfeed.data.util.Resource
import com.example.newsfeed.domain.repository.NewsRepository

/*
Here for this use case, we will search for certain news articles.
 */
class GetSearchedNewsHeadlines(private val newsRepository: NewsRepository) {

    suspend fun execute(country: String, searchQuery: String, page: Int): Resource<NewsResponse> {
        return newsRepository.getSearchedNewsHeadlines(country, searchQuery, page)
    }
}