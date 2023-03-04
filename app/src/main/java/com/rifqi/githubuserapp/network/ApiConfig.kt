package com.rifqi.githubuserapp.network

import com.rifqi.githubuserapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    val authInterceptor = Interceptor { chain ->
        val req = chain.request()
        val apiToken = BuildConfig.API_TOKEN
        val requestHeaders = req.newBuilder()
            .addHeader("Authorization", "$apiToken")
            .build()
        chain.proceed(requestHeaders)
    }

    fun getApiService(): ApiService {
        val apiURL = BuildConfig.API_URL
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("$apiURL")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)
    }
}