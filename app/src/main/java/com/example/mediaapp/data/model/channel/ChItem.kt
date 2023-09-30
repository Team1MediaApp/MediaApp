package com.example.mediaapp.data.model.channel


import com.google.gson.annotations.SerializedName

data class ChItem(
    @SerializedName("etag")
    val etag: String,
    @SerializedName("id")
    val id: Id,
    @SerializedName("kind")
    val kind: String,
    @SerializedName("snippet")
    val snippet: Snippet
)

data class Id(
    @SerializedName("channelId")
    val channelId: String,
    @SerializedName("kind")
    val kind: String
)