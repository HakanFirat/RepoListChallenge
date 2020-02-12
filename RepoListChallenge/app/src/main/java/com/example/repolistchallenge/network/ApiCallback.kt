package com.example.repolistchallenge.network

import com.example.repolistchallenge.network.model.Error
import com.example.repolistchallenge.utils.Constant
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class ApiCallBack<T> : Callback<T> {
    override fun onFailure(call: Call<T>, t: Throwable) {
        val apiError = ApiError(
            Error(
                ErrorAction.UnExpected,
                "500",
                Constant.API_CALL_ERROR_MESSAGE
            )
        )
        onError(apiError)
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            onSuccess(response.body())
        } else {
            val errorResponse: ApiError? =
                Gson().fromJson(response.errorBody()?.charStream(), ApiError::class.java)
            onError(errorResponse ?: ApiError.createErrorFromString(""))
        }
    }

    abstract fun onSuccess(data: T?)
    abstract fun onError(error: ApiError)
}