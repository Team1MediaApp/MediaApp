package com.example.mediaapp.detail

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class YoutubeDetailModel(
    @SerializedName("etag") // 리소스의 etag이다.
    val etag: String,
    @SerializedName("items") // 요청기준과 일치하는 동영상 목록
    val items: List<Item>,
    @SerializedName("kind") // API의 리소스의 유형을 식별한다.
    val kind: String,
    @SerializedName("nextPageToken") // 다음 페이지를 가져 올 수 있는 토큰
    val nextPageToken: String,
    @SerializedName("pageInfo") // 결과 세트의 페이징 정보를 캡슐화
    val pageInfo: PageInfo
) : Parcelable

@Parcelize
data class Snippet(
    @SerializedName("categoryId")
    val categoryId: String,
    @SerializedName("channelId")
    val channelId: String,
    @SerializedName("channelTitle")
    val channelTitle: String,
    @SerializedName("defaultAudioLanguage")
    val defaultAudioLanguage: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("liveBroadcastContent")
    val liveBroadcastContent: String,
    @SerializedName("localized")
    val localized: Localized,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("tags")
    val tags: List<String>,
    @SerializedName("thumbnails")
    val thumbnails: Thumbnails,
    @SerializedName("title")
    val title: String
): Parcelable

@Parcelize
data class Thumbnails(
    @SerializedName("default")
    val default: Default,
    @SerializedName("high")
    val high: High,
    @SerializedName("maxres")
    val maxres: Maxres,
    @SerializedName("medium")
    val medium: Medium,
    @SerializedName("standard")
    val standard: Standard
): Parcelable

@Parcelize
data class Standard(
    @SerializedName("height")
    val height: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
): Parcelable

@Parcelize
data class PageInfo(
    @SerializedName("resultsPerPage")
    val resultsPerPage: Int,
    @SerializedName("totalResults")
    val totalResults: Int
): Parcelable

@Parcelize
data class Medium(
    @SerializedName("height")
    val height: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
): Parcelable

@Parcelize
data class Maxres(
    @SerializedName("height")
    val height: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
): Parcelable

@Parcelize
data class Localized(
    @SerializedName("description")
    val description: String,
    @SerializedName("title")
    val title: String
): Parcelable

@Parcelize
data class Item(
    @SerializedName("etag")
    val etag: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("kind")
    val kind: String,
    @SerializedName("snippet")
    val snippet: Snippet
): Parcelable

@Parcelize
data class High(
    @SerializedName("height")
    val height: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
): Parcelable

@Parcelize
data class Default(
    @SerializedName("height")
    val height: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
): Parcelable