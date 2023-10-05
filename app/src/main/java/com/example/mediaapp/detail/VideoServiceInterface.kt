package com.example.mediaapp.detail

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoServiceInterface {
    @GET("videos")
    fun video(
        @Query("key") key: String,
        @Query("part") part : String,
        @Query("chart") chart: String,
        @Query("hl") hl: String,
        @Query("regionCode") regionCode: String,
        @Query("maxResults") maxResults: Int
    ): Call<YoutubeDetailModel?>?

    @GET("channels")
    suspend fun detailChannel(
        @Query("key")key:String,
        @Query("part") part : String,
        @Query("chart") chart: String,
        @Query("hl") hl: String,
        @Query("regionCode") regionCode: String,
        @Query("maxResults") maxResults: Int
    )
}