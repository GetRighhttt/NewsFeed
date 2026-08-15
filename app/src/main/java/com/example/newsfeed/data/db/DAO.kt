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

    @Query(
        """
        SELECT id FROM articles
        WHERE (:articleId IS NOT NULL AND articleId = :articleId)
           OR (:link IS NOT NULL AND link = :link)
           OR (:title IS NOT NULL AND title = :title)
        LIMIT 1
        """
    )
    suspend fun findExistingId(
        articleId: String?,
        link: String?,
        title: String?
    ): Int?

    @Transaction
    suspend fun upsert(results: Results) {
        val existingId = findExistingId(results.articleId, results.link, results.title)
        insert(results.copy(id = existingId ?: results.id))
    }

    @Query("SELECT * FROM articles")
    fun getAllArticles(): Flow<List<Results>>

    @Delete
    suspend fun deleteSavedNewsArticles(results: Results)
}
