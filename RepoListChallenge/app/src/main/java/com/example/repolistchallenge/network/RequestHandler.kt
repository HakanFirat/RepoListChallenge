package com.example.repolistchallenge.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

class RequestHandler(private val chain: Interceptor.Chain) {

    var activeReq: Request? = null
    private var original: Request = chain.request()

    private fun getNewRequest(postBody: (() -> RequestBody)? = null): Request {
        activeReq = getNewRequestBuilder(postBody).build()
        return activeReq!!
    }

    private fun getNewRequestBuilder(postBody: (() -> RequestBody)? = null): Request.Builder {
        return original.newBuilder()
            .addHeader("Cache-Control", "no-cache")
            .method(original.method(), original.body())
            .apply {
                postBody?.let {
                    post(it())
                }
            }
    }

    fun proceed(postBody: (() -> RequestBody)? = null): Response {
        return chain.proceed(getNewRequest(postBody)).also {
        }
    }

}