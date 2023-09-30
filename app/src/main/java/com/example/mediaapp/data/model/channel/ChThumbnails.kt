package com.example.mediaapp.data.model.channel


import com.google.gson.annotations.SerializedName

data class Thumbnails(
    @SerializedName("default")
    val default: Default,
    @SerializedName("high")
    val high: High,
    @SerializedName("medium")
    val medium: Medium
)

data class Default(
    @SerializedName("url")
    val url: String
)

data class High(
    @SerializedName("url")
    val url: String
)

data class Medium(
    @SerializedName("url")
    val url: String
)