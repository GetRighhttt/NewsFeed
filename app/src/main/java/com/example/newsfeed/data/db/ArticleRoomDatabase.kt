package com.example.newsfeed.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsfeed.data.model.Results

/**
 * Typical room database class except here we have a type converter.
 */
@Database(entities = [Results::class], version = 6, exportSchema = false)
abstract class ArticleRoomDatabase : RoomDatabase() {
    // abstract getter for Dao interface
    abstract fun getArticleDAO(): DAO
}