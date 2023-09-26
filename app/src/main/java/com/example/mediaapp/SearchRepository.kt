package com.example.mediaapp

import com.example.mediaapp.data.model.SearchResponse
import retrofit2.Response

interface SearchRepository {
    suspend fun search(
        key: String,
        q : String,
        chart : String,
        hl : String,
        maxResults: Int,
        regionCode: String,
    ): Response<SearchResponse>
}