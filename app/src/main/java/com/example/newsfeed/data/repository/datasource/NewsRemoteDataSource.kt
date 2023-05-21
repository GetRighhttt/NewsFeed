package com.example.newsfeed.data.repository.datasource

import com.example.newsfeed.data.model.NewsResponse
import retrofit2.Response

/*
Define abstract functions to communicate with API.
 */
interface NewsRemoteDataSource {

    suspend fun getTopHeadlines(topic: String, page: Int): Response<NewsResponse>
    suspend fun getSearchedNewsHeadlines(q: String, page: Int): Response<NewsResponse>

}