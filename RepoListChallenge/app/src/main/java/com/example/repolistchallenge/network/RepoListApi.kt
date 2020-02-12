package com.example.repolistchallenge.network

import com.example.repolistchallenge.model.RepoModel
import retrofit2.Call
import retrofit2.http.*

interface RepoListApi {

    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Call<List<RepoModel>>
}