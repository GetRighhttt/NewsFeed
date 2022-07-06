package com.example.newsfeed.domain.usecase

import com.example.newsfeed.data.model.NewsResponse
import com.example.newsfeed.data.util.Resource
import com.example.newsfeed.domain.repository.NewsRepository

/*
Here for this use case, we will search for certain news articles.
 */
class GetSearchedNews(private val newsRepository: NewsRepository) {

    suspend fun execute(searchQuery: String): Resource<NewsResponse> {
        return newsRepository.getSearchedNews(searchQuery)
    }
}