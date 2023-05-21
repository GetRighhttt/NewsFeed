package com.example.newsfeed.data.repository.datasourceimpl

import com.example.newsfeed.data.api.NewsApiService
import com.example.newsfeed.data.model.NewsResponse
import com.example.newsfeed.data.repository.datasource.NewsRemoteDataSource
import retrofit2.Response

/*
Data source implementation to our ApiService.

Must add parameters from the @Query of the @GET response as constructor parameters.

Our APIKey was already defined so we don't need a parameter for that.
 */
class NewsRemoteDataSourceImpl(
    private val newsApiService: NewsApiService
): NewsRemoteDataSource {
    override suspend fun getTopHeadlines(topic: String, page: Int): Response<NewsResponse> {
        return newsApiService.getTopHeadlines(topic, page)
    }

    override suspend fun getSearchedNewsHeadlines(
        q: String,
        page: Int
    ): Response<NewsResponse> {
        return newsApiService.getSearchedTopHeadlines(q, page)
    }
}