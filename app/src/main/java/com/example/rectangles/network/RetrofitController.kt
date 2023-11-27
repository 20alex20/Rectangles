package com.example.rectangles.network

import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.coroutines.resume

class OkHttpController(api: String) : RequestController {
    private val gson = GsonBuilder().create()
    private val client = OkHttpClient()
    private val request = Request.Builder().url("$api/floof").build()

    override suspend fun requestLink(): Result {
        return suspendCancellableCoroutine<Result> { continuation ->
            client.newCall(request).execute().use { response ->
                if (response.code != 200) {
                    continuation.resume(
                        Result.Error("Connection error: ${response.code}:${response.message}")
                    )
                } else if (response.body == null) {
                    continuation.resume(Result.Error("No body"))
                } else {
                    try {
                        val link = gson.fromJson(response.body!!.string(), Link::class.java)
                        continuation.resume(Result.Ok(link))
                    } catch (e: JsonSyntaxException) {
                        continuation.resume(Result.Error("${e.message}"))
                    }
                }
            }
        }
    }
}