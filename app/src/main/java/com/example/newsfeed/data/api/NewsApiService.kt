package com.example.newsfeed.data.api

import com.example.newsfeed.BuildConfig
import com.example.newsfeed.data.model.NewsResponse
import com.google.gson.internal.GsonBuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/*
Service to get the endpoints from our base url
 */
interface NewsApiService {
    /*
    Method definition to get the top headlines from our NewsResponse Model

    Each query serves as a new end point.
     */
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country")
        country: String,
        @Query("page")
        page: Int,
        @Query("apiKey")
        apiKey: String = BuildConfig.API_KEY
    ): Response<NewsResponse>

    /**
     * Define another function to get searched News Headlines
     */
    @GET("v2/top-headlines")
    suspend fun getSearchedTopHeadlines(
        @Query("country")
        country: String,
        @Query("q")
        searchQuery: String,
        @Query("page")
        page: Int,
        @Query("apiKey")
        apiKey: String = BuildConfig.API_KEY
    ): Response<NewsResponse>
}