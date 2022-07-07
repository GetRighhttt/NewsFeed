package com.example.newsfeed.domain.repository

import com.example.newsfeed.data.model.Article
import com.example.newsfeed.data.model.NewsResponse
import com.example.newsfeed.data.util.Resource
import kotlinx.coroutines.flow.Flow


/*
Define abstract functions for al of our use cases and repository implementation.

This repository Interface will serve as a layer between
the data and presentation layers of clean code architecture.

In clean architecture, we do not use any additional frameworks in the domain layer.

FLow used in this instance used to handle data asynchronously.
 */
interface NewsRepository {
    /*
    Get method for get use case.
     */
    suspend fun getNewsHeadlines(country: String, page: Int): Resource<NewsResponse>

    /*
    Get Search method for search use case.
     */
    suspend fun getSearchedNews(searchQuery: String): Resource<NewsResponse>

    /*
    Save method for save news case.
     */
    suspend fun saveNews(article: Article)

    /*
    Delete method for delete use case.
     */
    suspend fun deleteNews(article: Article)

    /*
    Get saved news method for getting the saved news articles.
     */
    fun getSavedNews(): Flow<List<Article>>
}