package com.example.mediaapp.data.model.channel


import com.google.gson.annotations.SerializedName

data class ChannelResponse(
    @SerializedName("etag")
    val etag: String,
    @SerializedName("items")
    val chItems: List<ChItem>,
    @SerializedName("kind")
    val kind: String,
    @SerializedName("nextPageToken")
    val nextPageToken: String,
    @SerializedName("pageInfo")
    val pageInfo: PageInfo,
    @SerializedName("regionCode")
    val regionCode: String
)
data class PageInfo(
    @SerializedName("resultsPerPage")
    val resultsPerPage: Int,
    @SerializedName("totalResults")
    val totalResults: Int
)