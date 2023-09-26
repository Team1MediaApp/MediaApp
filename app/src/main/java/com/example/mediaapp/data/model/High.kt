package com.example.mediaapp.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class High(
    @field:Json(name = "height")
    val height: Int,
    @field:Json(name = "url")
    val url: String,
    @field:Json(name = "width")
    val width: Int
)