package com.example.mediaapp

import com.example.mediaapp.data.api.RetrofitInstance.api
import com.example.mediaapp.data.model.channel.ChannelResponse
import com.example.mediaapp.data.model.video.SearchResponse
import retrofit2.Response

class VideoRepositoryImpl:VideoRepository {
    override suspend fun search(
        key : String,
        part: String,
        videoCategoryId: String,
        chart: String,
        hl: String,
        maxResults: Int,
        regionCode: String
    ): Response<SearchResponse> {
        return api.searchYoutube(key, part, videoCategoryId , chart, hl, maxResults, regionCode)
    }

    override suspend fun searchChannel(
        key : String,
        part: String,
        maxResults: Int,
        q: String,
        regionCode: String,
        type: String,
    ): Response<ChannelResponse> {
        return api.searchChannel(key,part,maxResults,q,regionCode,type)
    }
}

