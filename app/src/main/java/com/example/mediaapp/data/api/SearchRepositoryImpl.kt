package com.example.mediaapp.data.api

import com.example.mediaapp.model.YoutubeSearchResponse
import com.example.mediaapp.util.Constants

class SearchRepositoryImpl() : SearchRepository {
    override suspend fun getSearchImage(
        query: String,
        type: String,
        maxResult: Int
    ): YoutubeSearchResponse =
        NetworkClient.search.responseSearch(
            Constants.API_KEY,
            "snippet",
            query,
            maxResult,
            "KR",
            type
        )

    override suspend fun getSearchChannel(query: String): YoutubeSearchResponse =
        NetworkClient.search.responseChannels(Constants.API_KEY, "snippet", query)


}