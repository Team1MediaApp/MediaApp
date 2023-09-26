package com.example.mediaapp.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponse(
    @field:Json(name = "etag")
    val etag: String,
    @field:Json(name = "items")
    val items: List<Item>,
    @field:Json(name = "kind")
    val kind: String,
    @field:Json(name = "nextPageToken")
    val nextPageToken: String,
    @field:Json(name = "pageInfo")
    val pageInfo: PageInfo,
    @field:Json(name = "regionCode")
    val regionCode: String
)