package com.example.mediaapp.data.model.channel


import com.google.gson.annotations.SerializedName

data class Snippet(
    @SerializedName("country")
    val country: String,
    @SerializedName("customUrl")
    val customUrl: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("localized")
    val localized: Localized,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("thumbnails")
    val thumbnails: Thumbnails,
    @SerializedName("title")
    val title: String
)

data class Localized(
    @SerializedName("description")
    val description: String,
    @SerializedName("title")
    val title: String
)