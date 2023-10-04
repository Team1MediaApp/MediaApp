package com.example.mediaapp.data.api

import com.example.mediaapp.model.YoutubeSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeService {
    @GET("v3/search")
    suspend fun responseSearch(
        @Query("key") key: String,
        @Query("part") part: String,
        @Query("maxResults") maxResults: Int?,
        @Query("pageToken") pageToken: String?,
        @Query("q") query: String?,
        @Query("regionCode") regionCode: String?,
        @Query("type") type: String?,
    ): YoutubeSearchResponse

    @GET("v3/channels")
    suspend fun responseChannels(
        @Query("key") key: String,
        @Query("part") part: String,
//        @Query("categoryId") categoryId: String?,
        @Query("forUsername") channelName: String?,
//        @Query("hl") hl: String?,
//        @Query("id") channelId: String?,
//        @Query("managedByMe") managedByMe: Boolean? = null,
//        @Query("maxResults") maxResults: Int?,
//        @Query("mine") mine: Boolean? = null,
//        @Query("mySubscribers") mySubscribers: Boolean? = null,
//        @Query("onBehalfOfContentOwner") onBehalfOfContentOwner: String?,
//        @Query("pageToken") pageToken: String?,
    ): YoutubeSearchResponse

}