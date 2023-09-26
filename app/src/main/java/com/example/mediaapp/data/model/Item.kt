package com.example.mediaapp.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Item(
    @field:Json(name = "etag")
    val etag: String,
    @field:Json(name = "id")
    val id: Id,
    @field:Json(name = "kind")
    val kind: String,
    @field:Json(name = "snippet")
    val snippet: Snippet
)