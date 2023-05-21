package com.example.newsfeed.domain.repository

import com.example.newsfeed.data.model.Results
import com.example.newsfeed.data.model.NewsResponse
import com.example.newsfeed.data.util.Resource
import kotlinx.coroutines.flow.Flow


/*
Define abstract functions for all of our use cases and repository implementation.

This repository Interface will serve as a layer between
the data and presentation layers of clean code architecture.

In clean architecture, we do not use any additional frameworks (androidx) )in the domain layer.

FLow used in this instance used to handle data asynchronously.
 */
interface NewsRepository {
    /*
    Get method for get use case.
     */
    suspend fun getNewsHeadlines(q: String): Resource<NewsResponse>

    /*
    Get Search method for search use case.
     */
    suspend fun getSearchedNewsHeadlines(
        q: String
    ): Resource<NewsResponse>

    /*
    Save method for save news case for our Local data source.

    We pass article as the parameter because we want to save the article.
     */
    suspend fun saveNews(results: Results)

    /*
    Delete method for delete use case for our local data source.
     */
    suspend fun deleteSavedNewsArticles(results: Results)

    /*
    Get the list of saved news articles data from the database.

    So we return the list as a Flow because it's best practice for repositories,
    because it will cause unexpected threading issues.

    ROOM allows us to get the data as a Flow.

    Because it returns a stream, the data does not have to be suspended.
     */
    fun getSavedData(): Flow<List<Results>>
}