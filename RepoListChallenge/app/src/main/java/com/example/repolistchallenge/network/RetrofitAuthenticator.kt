package com.example.repolistchallenge.network

import okhttp3.Interceptor
import okhttp3.Response

class RetrofitAuthenticator: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response? {
        val requestHandler =
            RequestHandler(chain)
        return checkResponse(
            requestHandler.proceed(),
            ResponseErrorHandler(requestHandler)
        )
    }

    private fun checkResponse(
        response: Response,
        responseErrorHandler: ResponseErrorHandler
    ): Response? {
        return when {
            response.code() == 200 -> {
                response
            }
            (response.code() == 503 || response.code() == 504) -> {
                responseErrorHandler.onError(
                    response
                )
            }
            response.code() == 422 -> {
                val result = responseErrorHandler.getErrorList(response)
                responseErrorHandler.onErrors(
                    response,
                    result.first,
                    response.code().toString(),
                    result.second
                )
            }
            response.code() == 404 -> {
                responseErrorHandler.onError(response)
            }
            else -> {
                val result = responseErrorHandler.getErrorObject(response)
                responseErrorHandler.onError(
                    response,
                    result.second,
                    result.third?.Error?.ErrorCode,
                    result.third?.Error?.ErrorMessage
                )
            }
        }
    }
}