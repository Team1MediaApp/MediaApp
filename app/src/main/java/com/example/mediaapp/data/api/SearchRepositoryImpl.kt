package com.example.mediaapp.data.api

import com.example.mediaapp.Constants

class NetworkRepository {
    private val builder = NetworkClient.getBuilder().create(YoutubeService::class.java)

    suspend fun searchYoutube(
        query: String
    ) =
        builder.responseSearch(Constants.API_KEY, "snippet", query, 3, "KR")

}