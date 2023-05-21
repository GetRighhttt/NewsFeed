package com.example.newsfeed.data.repository.datasource

import com.example.newsfeed.data.model.Results
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {
    suspend fun saveArticleToDB(results: Results)

    fun getSavedData(): Flow<List<Results>>

    suspend fun deleteSavedNewsArticles(results: Results)
}