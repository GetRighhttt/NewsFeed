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
    @SerializedName("title")
    val title: String?,
    @SerializedName("author")
    val author: String?,
    @SerializedName("publishedDate")
    val published_date: String?,
    @SerializedName("link")
    val link: String?,
    @SerializedName("source")
    val rights: String?,
    @SerializedName("excerpt")
    val excerpt: String?,
    @SerializedName("summary")
    val summary: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("media")
    val media: String?
) : Serializable