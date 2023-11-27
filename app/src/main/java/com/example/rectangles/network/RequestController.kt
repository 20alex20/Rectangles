package com.example.rectangles.network

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
    @SerializedName("link") val notUsed: String,
)
