package com.example.mediaapp.data.api

import com.example.mediaapp.model.YoutubeChannelResponse
import com.example.mediaapp.model.YoutubeSearchResponse

interface SearchRepository {
    suspend fun getSearchImage(
        query: String,
        type: String,
        maxResult: Int = 10
    ): YoutubeSearchResponse

    suspend fun getSearchImageByPageToken(
        nextPageToken: String,
        maxResult: Int = 10,
        query: String,
    ): YoutubeSearchResponse

    suspend fun getSearchChannel(channelID: String): YoutubeChannelResponse
}