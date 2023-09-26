package com.example.mediaapp.data.api


import com.example.mediaapp.Constants
import com.example.mediaapp.data.model.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SearchInterface {

    @GET("search")
    suspend fun searchYoutube(
        @Query("key") key: String = Constants.API_KEY,
        @Query("q") q :String,
        @Query("part") part:String,
        @Query("channelType") channelType:String,
        @Query("maxResults")maxResults:Int,
        @Query("type")type:String
    ): Response<SearchResponse>
}