package com.example.newsfeed.presentation.model

import com.example.newsfeed.data.model.Results
import java.io.Serializable

data class ArticleArgs(
    val id: Int?,
    val articleId: String?,
    val author: String?,
    val title: String?,
    val link: String?,
    val description: String?,
    val pubDate: String?,
    val imageUrl: String?
) : Serializable

fun Results.toArticleArgs() = ArticleArgs(
    id = id,
    articleId = articleId,
    author = author,
    title = title,
    link = link,
    description = description,
    pubDate = pubDate,
    imageUrl = image_url
)

fun ArticleArgs.toResults() = Results(
    id = id,
    articleId = articleId,
    author = author,
    title = title,
    link = link,
    description = description,
    pubDate = pubDate,
    image_url = imageUrl
)
