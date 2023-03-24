package com.romka_po.driveassistant.adapters

import com.romka_po.driveassistant.interfaces.ApiClient
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIAdapter {
    val apiClient: ApiClient = Retrofit.Builder()
        .baseUrl("https://cars-base.ru")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiClient::class.java)
}