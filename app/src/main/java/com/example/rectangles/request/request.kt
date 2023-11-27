package com.example.rectangles.request

import androidx.compose.runtime.MutableState
import com.example.rectangles.network.OkHttpController
import com.example.rectangles.network.Result
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


fun request(link: MutableState<String>, okHttpController: OkHttpController) {
    if (link.value == "") {
        CoroutineScope(Dispatchers.IO + CoroutineExceptionHandler { _, e -> println(e) }).launch {
            val linkResult = okHttpController.requestLink()
            if (linkResult is Result.Ok) {
                withContext(Dispatchers.Main) {
                    link.value = linkResult.link.link
                }
            }
        }
    }
}
