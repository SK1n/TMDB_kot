package com.example.tmdb.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private const val URL = "https://api.themoviedb.org/3/"
    private val okHttps = OkHttpClient.Builder()
    private val builder = Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create()).client(
        okHttps.build())
    private val retrofit = builder.build()

    fun<T> buildService (serviceType: Class<T>):T {
        return retrofit.create(serviceType)
    }
}