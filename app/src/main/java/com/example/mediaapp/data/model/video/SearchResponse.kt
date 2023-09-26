package com.example.mediaapp.data.model.video


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("etag")
    val etag: String,
    @SerializedName("items")
    val items: MutableList<Item>,
    @SerializedName("kind")
    val kind: String,
    @SerializedName("nextPageToken")
    val nextPageToken: String,
    @SerializedName("pageInfo")
    val pageInfo: PageInfo
)

data class PageInfo(
    @SerializedName("resultsPerPage")
    val resultsPerPage: Int,
    @SerializedName("totalResults")
    val totalResults: Int
)