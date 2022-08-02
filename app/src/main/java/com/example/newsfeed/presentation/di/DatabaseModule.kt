package com.example.newsfeed.presentation.di

import android.app.Application
import androidx.room.Dao
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsfeed.data.db.ArticleRoomDatabase
import com.example.newsfeed.data.db.DAO
import com.example.newsfeed.data.model.Article
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Here is where we actually build the database, and provides method for the DAO.
 */
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun providesNewsDatabase(app: Application): ArticleRoomDatabase {
        return Room.databaseBuilder(
            app, ArticleRoomDatabase::class.java,
            "news_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesDao(articleRoomDatabase: ArticleRoomDatabase) : DAO {
        return articleRoomDatabase.getArticleDAO()
    }

}