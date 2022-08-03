package com.example.newsfeed.data.repository.repositoryimpl

import com.example.newsfeed.data.model.Article
import com.example.newsfeed.data.model.NewsResponse
import com.example.newsfeed.data.repository.datasource.NewsLocalDataSource
import com.example.newsfeed.data.repository.datasource.NewsRemoteDataSource
import com.example.newsfeed.data.util.Resource
import com.example.newsfeed.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

/*
Class to implement all the methods we created in our regular repository of our domain layer.

This class overrides all the methods we defined in our use cases.

This class serves as the implementation from our repository in domain, to our data package.

We are going to use our Resource<T> class to keep track of our State of our API.
 */

class NewsRepositoryImpl(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: NewsLocalDataSource
) : NewsRepository {
    override suspend fun getNewsHeadlines(country: String, page: Int): Resource<NewsResponse> {
        return responseToResource(newsRemoteDataSource.getTopHeadlines(country, page))
    }

    override suspend fun getSearchedNewsHeadlines(
        country: String,
        searchQuery: String,
        page: Int
    ): Resource<NewsResponse> {
        return responseToResource(
            newsRemoteDataSource.getSearchedNewsHeadlines(
                country,
                searchQuery,
                page
            )
        )
    }

    /*
    Method to determine the state of the NewsResponse,
    and determine if it is successful or error.
     */
    private fun responseToResource(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    override suspend fun saveNews(article: Article) {
        newsLocalDataSource.saveArticleToDB(article)
    }

    override suspend fun deleteSavedNewsArticles(article: Article) {
        newsLocalDataSource.deleteSavedNewsArticles(article)
    }

    override fun getSavedData(): Flow<List<Article>> {
        return newsLocalDataSource.getSavedData()
    }
}