package com.example.rectangles.request

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName


interface RequestController {
    suspend fun requestLink(): Result
}

sealed interface Result {
    data class Ok(val link: Link) : Result
    data class Error(val error: String) : Result
}

data class Link(
    @SerializedName("image") val link: String,
    @SerializedName("link") val notused: String,
)

val gsonWithDate: Gson
    get() = GsonBuilder()
        .setDateFormat("yyyy-HM-dd HH:mm:ss")
        .create()
