package com.example.newsfeed.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.newsfeed.data.model.Article

/**
 * Typical room database class except here we have a type converter.
 */
@Database(entities = [Article::class], version = 3, exportSchema = false)
abstract class ArticleRoomDatabase : RoomDatabase() {
    // abstract getter for Dao interface
    abstract fun getArticleDAO(): DAO
}