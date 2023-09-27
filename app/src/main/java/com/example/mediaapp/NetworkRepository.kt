package com.example.mediaapp

class NetworkRepository {
    private val builder = NetworkClient.getBuilder().create(YoutubeService::class.java)

    suspend fun searchYoutube(
        query: String
    ) =
        builder.responseSearch("AIzaSyDeKACf7mU3EjsNsrMNn4-4YikctqgXxzQ", "snippet", query, 3, "KR")

}