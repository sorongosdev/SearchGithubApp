package com.sorongos.searchgithubapp

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {
    private const val BASE_URL = "https://api.github.com"

    //공통헤더
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            val request = it.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer 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")
                .build()
            it.proceed(request) // 실제 적용
        }
        .build()

    //retrofit 객체 전역으로 선언
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}