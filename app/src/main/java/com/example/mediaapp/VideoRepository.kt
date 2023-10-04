package com.example.mediaapp

import com.example.mediaapp.data.model.channel.ChannelResponse
import com.example.mediaapp.data.model.video.SearchResponse
import retrofit2.Response
import retrofit2.http.Query

interface VideoRepository {
    suspend fun search(
        key: String,
        part: String,
        videoCategoryId: String,
        chart: String,
        hl: String,
        maxResults: Int,
        regionCode: String,
        pageToken: String,
    ): Response<SearchResponse>

    suspend fun searchChannel(
        key: String,
        part: String,
        maxResults: Int,
        q: String,
        regionCode: String,
        type: String,
    ): Response<ChannelResponse>
}
