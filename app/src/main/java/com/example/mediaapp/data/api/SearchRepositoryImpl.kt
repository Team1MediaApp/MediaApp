package com.example.mediaapp.data.api

import com.example.mediaapp.Constants
import com.example.mediaapp.model.YoutubeSearchResponse

class SearchRepositoryImpl() : SearchRepository {
    override suspend fun getSearchImage(query: String, type: String): YoutubeSearchResponse =
        NetworkClient.search.responseSearch(Constants.API_KEY, "snippet", query, 3, "KR", type)

    override suspend fun getSearchChannel(query: String): YoutubeSearchResponse =
        NetworkClient.search.responseChannels(Constants.API_KEY, "snippet", query)


}