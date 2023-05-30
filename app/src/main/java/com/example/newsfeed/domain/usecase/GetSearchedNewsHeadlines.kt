package com.example.newsfeed.domain.usecase

import android.graphics.DiscretePathEffect
import com.example.newsfeed.data.model.NewsResponse
import com.example.newsfeed.data.util.Resource
import com.example.newsfeed.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

/*
Here for this use case, we will search for certain news articles.
 */
class GetSearchedNewsHeadlines(private val newsRepository: NewsRepository) {

    suspend fun execute(q: String): Resource<NewsResponse> {
        val news = withContext(Dispatchers.IO) {
            newsRepository.getSearchedNewsHeadlines(q)
        }
        return news
    }
}