package com.example.newsfeed.data.api

import com.example.newsfeed.data.model.NewsResponse
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
    'x-api-key': 'YOUR_API_KEY'
     */

    // https://newsdata.io/api/1/latest?apikey=pub_5470442430656bf6f0c255d047e0993beca1b&q=pizza
    @GET("api/1/latest")
    suspend fun getTopHeadlines(
        @Query("apikey")
        apikey: String = "pub_225267ae16ee0419ec31a02756dca11d12937",
        @Query("q")
        q: String = "Sports",
        @Query("country")
        countries: List<String> = listOf("US"),
        @Query("language")
        lang: String = "en"
    ): Response<NewsResponse>

    /**
     * Define another function to get searched News Headlines
     */
    @GET("api/1/latest")
    suspend fun getSearchedTopHeadlines(
        @Query("q")
        q: String,
        @Query("apikey")
        apikey: String = "pub_225267ae16ee0419ec31a02756dca11d12937",
        @Query("country")
        countries: List<String> = listOf("US"),
        @Query("language")
        lang: String = "en"
    ): Response<NewsResponse>
}