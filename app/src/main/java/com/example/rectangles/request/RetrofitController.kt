package com.example.rectangles.request

import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.coroutines.resume

class OkHttpController(private val api: String) : RequestController {
    override suspend fun requestLink(): Result {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("$api/floof")
            .build()

        return suspendCancellableCoroutine<Result> { continuation ->
            client.newCall(request).execute().use { response ->
                if (response.code != 200) {
                    continuation.resume(
                        Result.Error("Request troubles: ${response.code}:${response.message}")
                    )
                } else if (response.body == null) {
                    continuation.resume(Result.Error("No body"))
                } else {
                    try {
                        val link =
                            gsonWithDate.fromJson(response.body!!.string(), Link::class.java)
                        continuation.resume(Result.Ok(link))
                    } catch (e: JsonSyntaxException) {
                        continuation.resume(Result.Error("${e.message}"))
                    }
                }
            }
        }
    }
}