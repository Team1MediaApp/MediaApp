package com.example.mediaapp.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class YoutubeChannelResponse(
    @SerializedName("etag")
    val etag: String?,
    @SerializedName("items")
    val items: List<Item?>?,
    @SerializedName("kind")
    val kind: String?,
    @SerializedName("pageInfo")
    val pageInfo: PageInfo?
) :Serializable

data class Snippet(
    @SerializedName("country")
    val country: String?,
    @SerializedName("customUrl")
    val customUrl: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("localized")
    val localized: Localized?,
    @SerializedName("publishedAt")
    val publishedAt: String?,
    @SerializedName("thumbnails")
    val thumbnails: Thumbnails?,
    @SerializedName("title")
    val title: String?
)

data class Statistics(
    @SerializedName("hiddenSubscriberCount")
    val hiddenSubscriberCount: Boolean?,
    @SerializedName("subscriberCount")
    val subscriberCount: String?,
    @SerializedName("videoCount")
    val videoCount: String?,
    @SerializedName("viewCount")
    val viewCount: String?
)

data class Medium(
    @SerializedName("height")
    val height: Int?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("width")
    val width: Int?
)

data class PageInfo(
    @SerializedName("resultsPerPage")
    val resultsPerPage: Int?,
    @SerializedName("totalResults")
    val totalResults: Int?
)

data class Thumbnails(
    @SerializedName("default")
    val default: Default?,
    @SerializedName("high")
    val high: High?,
    @SerializedName("medium")
    val medium: Medium?
)

data class Localized(
    @SerializedName("description")
    val description: String?,
    @SerializedName("title")
    val title: String?
)

data class Item(
    @SerializedName("etag")
    val etag: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("kind")
    val kind: String?,
    @SerializedName("snippet")
    val snippet: Snippet?,
    @SerializedName("statistics")
    val statistics: Statistics?
)

data class High(
    @SerializedName("height")
    val height: Int?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("width")
    val width: Int?
)

data class Default(
    @SerializedName("height")
    val height: Int?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("width")
    val width: Int?
)