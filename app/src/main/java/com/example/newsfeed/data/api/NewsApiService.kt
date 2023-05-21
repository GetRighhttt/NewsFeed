package com.example.newsfeed.data.api

import com.example.newsfeed.data.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/*
Service to get the endpoints from our base url
 */
interface NewsApiService {
    /*
    Method definition to get the top headlines from our NewsResponse Model

    Each query serves as a new end point.
     */
    @Headers("x-api-key: bHG5x7DTwN9x-_XR0e1i6taIPMF1kqYtNVcmFTD1ZBI")
    @GET("v2/latest_headlines")
    suspend fun getTopHeadlines(
        @Query("topic")
        topic: String,
        @Query("page")
        page: Int,
        @Query("countries")
        countries: Array<String> = arrayOf("US"),
        @Query("lang")
        lang: Array<String> = arrayOf("en"),
        @Query("when")
        time_Period: String = "7d",
        @Query("ranked_only")
        ranked_only: String = "True"
    ): Response<NewsResponse>

    /**
     * Define another function to get searched News Headlines
     */
    @Headers("x-api-key: bHG5x7DTwN9x-_XR0e1i6taIPMF1kqYtNVcmFTD1ZBI")
    @GET("v2/search")
    suspend fun getSearchedTopHeadlines(
        @Query("q")
        q: String,
        @Query("page")
        page: Int,
        @Query("topic")
        topic: String = "news",
        @Query("countries")
        countries: Array<String> = arrayOf("US"),
        @Query("lang")
        lang: Array<String> = arrayOf("en"),
        @Query("ranked_only")
        ranked_only: Boolean = true,
        @Query("sort_by")
        sort_by: String = "date"
    ): Response<NewsResponse>
}