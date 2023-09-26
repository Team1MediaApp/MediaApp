package com.example.mediaapp

import com.example.mediaapp.data.model.SearchResponse
import retrofit2.Response

interface SearchRepository {
    suspend fun search(
        key: String,
        q : String,
        part: String,
        channelType: String,
        maxResults: Int,
        type: String,
    ): Response<SearchResponse>
}