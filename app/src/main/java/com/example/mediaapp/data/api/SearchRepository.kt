package com.example.mediaapp.data.api

import com.example.mediaapp.model.YoutubeSearchResponse

interface SearchRepository {
    suspend fun getSearchImage(
        query: String,
        type: String,
        maxResult: Int = 3
    ): YoutubeSearchResponse

    suspend fun getSearchChannel(query: String): YoutubeSearchResponse
}