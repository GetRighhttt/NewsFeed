package com.example.newsfeed.data.repository.datasourceimpl

import com.example.newsfeed.data.api.NewsApiService
import com.example.newsfeed.data.model.NewsResponse
import com.example.newsfeed.data.repository.datasource.NewsRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

/*
Data source implementation to our ApiService.

Must add parameters from the @Query of the @GET response as constructor parameters.

Our APIKey was already defined so we don't need a parameter for that.
 */
class NewsRemoteDataSourceImpl(
    private val newsApiService: NewsApiService
): NewsRemoteDataSource {
    override suspend fun getTopHeadlines(): Response<NewsResponse> {
        return withContext(Dispatchers.IO) {
            newsApiService.getTopHeadlines()
        }
    }

    override suspend fun getSearchedNewsHeadlines(
        q: String
    ): Response<NewsResponse> {
        return withContext(Dispatchers.IO) {
            newsApiService.getSearchedTopHeadlines(q)
        }
    }
}