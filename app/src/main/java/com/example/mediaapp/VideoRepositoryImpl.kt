package com.example.mediaapp

import com.example.mediaapp.data.api.RetrofitInstance.api
import com.example.mediaapp.data.model.video.SearchResponse
import retrofit2.Response

class VideoRepositoryImpl:VideoRepository {
    override suspend fun search(
        key : String,
        part: String,
        q: String,
        chart: String,
        hl: String,
        maxResults: Int,
        regionCode: String
    ): Response<SearchResponse> {
        return api.searchYoutube(key, part, q , chart, hl, maxResults, regionCode)
    }
}