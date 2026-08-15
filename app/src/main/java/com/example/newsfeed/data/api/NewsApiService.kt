package com.example.newsfeed.data.api

import com.example.newsfeed.BuildConfig
import com.example.newsfeed.data.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/*
Service to get the endpoints from our base url
 */
interface NewsApiService {

    companion object {
        const val BASE_URL = "https://newsdata.io/"
    }

    /*
    Each query serves as a new end point.
    'x-api-key': 'YOUR_API_KEY'
     */
    @GET("api/1/news")
    suspend fun getTopHeadlines(
        @Query("apikey")
        apiKey: String = BuildConfig.NEWS_DATA_API_KEY,
        @Query("q")
        q: String = "news",
        @Query("country")
        countries: Array<String> = arrayOf("us"),
        @Query("language")
        lang: Array<String> = arrayOf("en")
    ): Response<NewsResponse>

    /**
     * Define another function to get searched News Headlines
     */
    @GET("api/1/news")
    suspend fun getSearchedTopHeadlines(
        @Query("q")
        q: String,
        @Query("apikey")
        apiKey: String = BuildConfig.NEWS_DATA_API_KEY,
        @Query("country")
        countries: Array<String> = arrayOf("US"),
        @Query("language")
        lang: Array<String> = arrayOf("en")
    ): Response<NewsResponse>
}
