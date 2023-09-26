package com.example.mediaapp.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PageInfo(
    @field:Json(name = "resultsPerPage")
    val resultsPerPage: Int,
    @field:Json(name = "totalResults")
    val totalResults: Int
)