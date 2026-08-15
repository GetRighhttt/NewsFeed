package com.example.newsfeed.data.model

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("Status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int,
    @SerializedName("results")
    val results: List<Results>?
)
