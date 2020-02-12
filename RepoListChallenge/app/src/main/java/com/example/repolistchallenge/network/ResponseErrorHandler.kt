package com.example.repolistchallenge.network

import com.example.repolistchallenge.network.model.Error
import com.example.repolistchallenge.utils.Constant
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import java.lang.Exception
import java.lang.StringBuilder
import java.lang.reflect.Type

class ResponseErrorHandler(private val requestHandler: RequestHandler) {

    fun onError(
        response: Response?,
        contentType: MediaType? = response?.body()?.contentType(),
        errorCode: String? = response?.code().toString(),
        errorMessage: String? = Constant.API_CALL_ERROR_MESSAGE,
        errorAction: ErrorAction? = ErrorAction.UnExpected
    ): Response? = response?.newBuilder()?.body(
        ResponseBody.create(
            contentType,
            Gson().toJson(
                ApiError(
                    Error(
                        errorAction,
                        errorCode ?: "0",
                        errorMessage ?: Constant.API_CALL_ERROR_MESSAGE
                    )
                )
            )
        )
    )?.build()

    fun onErrors(
        response: Response,
        contentType: MediaType? = response.body()?.contentType(),
        errorCode: String? = response.code().toString(),
        errorMessage: List<ApiListError>? = emptyList(),
        errorAction: ErrorAction = ErrorAction.UnExpected
    ): Response {
        val errorBuilder = StringBuilder("")
        if (errorMessage.isNullOrEmpty())
            errorBuilder.append(Constant.API_CALL_ERROR_MESSAGE)
        else for (item in errorMessage)
            errorBuilder.append(item.Message ?: "")

        return response.newBuilder()
            .body(
                ResponseBody.create(
                    contentType,
                    Gson().toJson(
                        ApiError(
                            Error(
                                errorAction,
                                errorCode ?: "0",
                                errorBuilder.toString()
                            )
                        )
                    )
                )
            )
            .build()
    }

    fun getErrorObject(response: Response): Triple<ErrorAction?, MediaType?, ApiError?> {
        val responseBody = response.body()
        val contentType = responseBody?.contentType()
        var errorResponse: ApiError = ApiError.createErrorFromString()
        try {
            errorResponse = Gson().fromJson(response.body()?.charStream(), ApiError::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val errorAction = errorResponse.Error.Action
        return Triple(errorAction, contentType, errorResponse)
    }

    fun getErrorList(response: Response): Pair<MediaType?, List<ApiListError>> {
        val responseBody = response.body()
        val contentType = responseBody?.contentType()
        val listType: Type = object : TypeToken<ArrayList<ApiListError?>?>() {}.type
        val errorResponse =
            Gson().fromJson<List<ApiListError>>(response.body()?.string() ?: "", listType)
        return Pair(contentType, errorResponse ?: emptyList())
    }
}