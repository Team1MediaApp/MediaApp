package com.example.mediaapp.data.api

import com.example.mediaapp.model.YoutubeChannelResponse
import com.example.mediaapp.model.YoutubeSearchResponse
import com.example.mediaapp.util.Constants
import retrofit2.http.Query

class SearchRepositoryImpl() : SearchRepository {
    override suspend fun getSearchImage(
        query: String,
        type: String,
        maxResult: Int
    ): YoutubeSearchResponse =
        NetworkClient.search.responseSearch(
            Constants.API_KEY,
            "snippet",
            maxResult,
            null,
            query,
            "KR",
            type,
        )

    override suspend fun getSearchImageByPageToken(
        nextPageToken: String,
        maxResult: Int,
        query: String,
    ): YoutubeSearchResponse = NetworkClient.search.responseSearch(
        Constants.API_KEY,
        "snippet",
        maxResult,
        nextPageToken,
        query,
        null,
        null,
    )

    override suspend fun getSearchChannel(channelID: String): YoutubeChannelResponse =
        NetworkClient.search.responseChannels(Constants.API_KEY, "snippet, statistics", channelID)
}