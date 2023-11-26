package com.example.rectangles.request

import retrofit2.Response
import retrofit2.http.GET


interface FoxApi {
    @GET("/floof")
    suspend fun link(): Response<Link>
}