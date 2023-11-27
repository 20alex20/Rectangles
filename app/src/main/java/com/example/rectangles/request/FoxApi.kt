package com.example.rectangles.request

import retrofit.Call
import retrofit.Response
import retrofit.http.GET
import rx.Single


interface FoxApi {
    @GET("/floof")
    suspend fun link(): Call<Link>
}