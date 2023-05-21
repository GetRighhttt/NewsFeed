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
    override suspend fun getTopHeadlines(topic: String): Response<NewsResponse> {
        return newsApiService.getTopHeadlines(q = topic)
    }

    override suspend fun getSearchedNewsHeadlines(
        q: String
    ): Response<NewsResponse> {
        return newsApiService.getSearchedTopHeadlines(q)
    }
}