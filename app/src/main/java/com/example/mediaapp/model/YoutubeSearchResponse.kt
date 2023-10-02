package com.example.mediaapp.model


import com.google.gson.annotations.SerializedName

data class YoutubeSearchModel(
    @SerializedName("etag")
    val etag: String,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("kind")
    val kind: String,
    @SerializedName("nextPageToken")
    val nextPageToken: String,
    @SerializedName("pageInfo")
    val pageInfo: PageInfo,
    @SerializedName("regionCode")
    val regionCode: String
) {
    data class Item(
        @SerializedName("etag")
        val etag: String,
        @SerializedName("id")
        val id: Id,
        @SerializedName("kind")
        val kind: String,
        @SerializedName("snippet")
        val snippet: Snippet
    ) {
        data class Id(
            @SerializedName("kind")
            val kind: String,
            @SerializedName("videoId")
            val videoId: String
        )

        data class Snippet(
            @SerializedName("channelId")
            val channelId: String,
            @SerializedName("channelTitle")
            val channelTitle: String,
            @SerializedName("description")
            val description: String,
            @SerializedName("liveBroadcastContent")
            val liveBroadcastContent: String,
            @SerializedName("publishTime")
            val publishTime: String,
            @SerializedName("publishedAt")
            val publishedAt: String,
            @SerializedName("thumbnails")
            val thumbnails: Thumbnails,
            @SerializedName("title")
            val title: String
        ) {
            data class Thumbnails(
                @SerializedName("default")  // 기본 썸네일 이미지, 120x90(pixels)
                val default: Default,
                @SerializedName("high") // 고해상도 480x360
                val high: High,
                @SerializedName("medium")
                val medium: Medium
            ) {

                data class Default(
                    @SerializedName("height")
                    val height: Int,
                    @SerializedName("url")
                    val url: String,
                    @SerializedName("width")
                    val width: Int
                )

                data class High(
                    @SerializedName("height")
                    val height: Int,
                    @SerializedName("url")
                    val url: String,
                    @SerializedName("width")
                    val width: Int
                )

                data class Medium(
                    @SerializedName("height")
                    val height: Int,
                    @SerializedName("url")
                    val url: String,
                    @SerializedName("width")
                    val width: Int
                )
            }
        }
    }

    data class PageInfo(
        @SerializedName("resultsPerPage")
        val resultsPerPage: Int,
        @SerializedName("totalResults")
        val totalResults: Int
    )
}