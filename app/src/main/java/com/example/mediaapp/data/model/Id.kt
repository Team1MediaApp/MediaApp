package com.example.mediaapp.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Id(
    @field:Json(name = "kind")
    val kind: String,
    @field:Json(name = "videoId")
    val videoId: String
)