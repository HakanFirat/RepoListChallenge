package com.example.repolistchallenge.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.repolistchallenge.network.ApiError
import com.example.repolistchallenge.network.ErrorAction
import com.example.repolistchallenge.network.State

open class BaseViewModel: ViewModel() {
    var state: MutableLiveData<State> = MutableLiveData()

    companion object {
        fun a() : Int = 1
    }

    fun setSuccessState(data: Any) {
        state.value = State.Success(data)
    }

    fun setLoadingState() {
        state.value = State.Loading()
    }

    fun setErrorState() {
        state.value = State.Error(ApiError.createErrorFromString())
    }

    fun setErrorState(error: String?) {
        state.value = State.Error(ApiError.createErrorFromString(error))
    }

    fun setErrorState(error: String?, errorAction: ErrorAction) {
        state.value = State.Error(ApiError.createErrorFromString(error, "0",errorAction))
    }

    fun setErrorState(error: ApiError?) {
        state.value = State.Error(error)
    }
}