package com.example.newsfeed.data.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

/**
 * This is the only class we will need to use ROOM database for as we
 * are only looking to save articles.
 *
 * @Entity used to mark ROOM.
 */
@Parcelize
@Entity(tableName = "articles")
data class Results(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @SerializedName("article_id")
    val articleId: String?,
    @SerializedName("author")
    val author: String?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("link")
    val link: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("pubDate")
    val pubDate: String?,
    @SerializedName("image_url")
    val image_url: String?
) : Parcelable