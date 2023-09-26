package com.example.mediaapp.data.api


import com.example.mediaapp.Constants
import com.example.mediaapp.data.model.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchInterface {

    @GET("videos")
    suspend fun searchYoutube(
        @Query("key") key: String = Constants.API_KEY,
        @Query("q") q :String,
        @Query("chart") chart:String,
        @Query("hl") hl:String,
        @Query("maxResults")maxResults:Int,
        @Query("regionCode")regionCode:String,
    ) : Response<SearchResponse>
}