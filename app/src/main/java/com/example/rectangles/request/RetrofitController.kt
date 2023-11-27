package com.example.rectangles.request

import retrofit.GsonConverterFactory
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory


class RetrofitController(api: String) : RequestController {

    private val retrofit = Retrofit.Builder()
        .baseUrl(api)
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .addConverterFactory(
            GsonConverterFactory.create(gsonWithDate)
        )
        .build()

    private val foxApi = retrofit.create(FoxApi::class.java)

    override suspend fun requestLink(): Result {
        val response = foxApi.link().execute()
        return if (response.isSuccess) {
            response.body()?.let {
                Result.Ok(it)
            } ?: Result.Error("Empty link")
        } else {
            Result.Error(response.code().toString())
        }
    }
}