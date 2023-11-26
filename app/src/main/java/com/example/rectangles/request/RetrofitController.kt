package com.example.rectangles.request

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitController(api: String) : RequestController {

    private val retrofit = Retrofit.Builder()
        .baseUrl(api)
        .addConverterFactory(
            GsonConverterFactory.create(gsonWithDate)
        )
        .build()

    private val foxApi = retrofit.create(FoxApi::class.java)

    override suspend fun requestLink(): Result {
        val response = foxApi.link()
        return if (response.isSuccessful) {
            response.body()?.let {
                Result.Ok(it)
            } ?: Result.Error("Empty link")
        } else {
            Result.Error(response.code().toString())
        }
    }
}