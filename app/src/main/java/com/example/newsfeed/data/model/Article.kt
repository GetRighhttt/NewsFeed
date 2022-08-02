package com.example.newsfeed.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * This is the only class we will need to use ROOM database for as we
 * are only looking to save articles.
 *
 * @Entity used to mark ROOM.
 */

@Entity(tableName = "articles")
data class Article(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @SerializedName("author")
    val author: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("publishedAt")
    val publishedAt: String?,
    @SerializedName("source")
    val source: Source?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("urlToImage")
    val urlToImage: String?
) : Serializable