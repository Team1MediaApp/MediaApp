package com.example.mediaapp.data.model.channel


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ChItem(
    @SerializedName("etag")
    val etag: String,
    @SerializedName("id")
    val id: Id,
    @SerializedName("kind")
    val kind: String,
    @SerializedName("snippet")
    val snippet: Snippet
) : Serializable

data class Id(
    @SerializedName("channelId")
    val channelId: String,
    @SerializedName("kind")
    val kind: String
) : Serializable