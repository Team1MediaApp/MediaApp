package com.example.mediaapp.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Snippet(
    @field:Json(name = "channelId")
    val channelId: String,
    @field:Json(name = "channelTitle")
    val channelTitle: String,
    @field:Json(name = "description")
    val description: String,
    @field:Json(name = "liveBroadcastContent")
    val liveBroadcastContent: String,
    @field:Json(name = "publishTime")
    val publishTime: String,
    @field:Json(name = "publishedAt")
    val publishedAt: String,
    @field:Json(name = "thumbnails")
    val thumbnails: Thumbnails,
    @field:Json(name = "title")
    val title: String
)