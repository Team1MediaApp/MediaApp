package com.example.mediaapp

import com.example.mediaapp.data.model.video.SearchResponse
import retrofit2.Response

interface VideoRepository {
    suspend fun search(
        key: String,
        part: String,
        q : String,
        chart : String,
        hl : String,
        maxResults: Int,
        regionCode: String,
    ): Response<SearchResponse>
}