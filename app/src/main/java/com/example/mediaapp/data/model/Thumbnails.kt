package com.example.mediaapp.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Thumbnails(
    @field:Json(name = "default")
    val default: Default,
    @field:Json(name = "high")
    val high: High,
    @field:Json(name = "medium")
    val medium: Medium
)