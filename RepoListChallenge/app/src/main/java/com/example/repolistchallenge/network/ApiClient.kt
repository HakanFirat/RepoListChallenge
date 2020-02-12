package com.example.repolistchallenge.network

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {

    private var okBuilder: OkHttpClient.Builder? = null
    private var adapterBuilder: Retrofit.Builder? = null

    init {
        createDefaultAdapter()
    }

    private fun createDefaultAdapter() {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss")

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        okBuilder = OkHttpClient
            .Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)

        var baseUrl = "https://api.github.com"
        if (!baseUrl.endsWith("/"))
            baseUrl = "$baseUrl/"

        okBuilder!!.addInterceptor(getAuthInterceptor())
        adapterBuilder =
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .baseUrl(baseUrl)
    }

    fun <S> createService(service: Class<S>): S {
        return adapterBuilder!!.client(okBuilder!!.build()).build().create(service)
    }

    companion object {
        val instance: ApiClient by lazy { ApiClient() }
    }

    private fun getAuthInterceptor(): Interceptor {
        return RetrofitAuthenticator()
    }
}