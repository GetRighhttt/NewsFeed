package com.example.newsfeed.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsfeed.data.model.Article
import kotlinx.coroutines.flow.Flow


/*
Here we will define the methods needed to save the article instance.

Typically if we want to use more CRUD functionalities, we would save them here.
 */
@Dao
interface DAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Query("SELECT * FROM articles")
    fun getAllArticles(): Flow<List<Article>>
}