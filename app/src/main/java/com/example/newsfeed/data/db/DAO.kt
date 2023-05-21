package com.example.newsfeed.data.db

import androidx.room.*
import com.example.newsfeed.data.model.Results
import kotlinx.coroutines.flow.Flow


/*
Here we will define the methods needed to save the article instance.

Typically if we want to use more CRUD functionalities, we would save them here.
 */
@Dao
interface DAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(results: Results)

    @Query("SELECT * FROM articles")
    fun getAllArticles(): Flow<List<Results>>

    @Delete
    suspend fun deleteSavedNewsArticles(results: Results)
}