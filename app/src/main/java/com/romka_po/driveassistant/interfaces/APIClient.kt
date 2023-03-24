package com.romka_po.driveassistant.interfaces

import com.romka_po.driveassistant.model.Brand
import com.romka_po.driveassistant.model.Model
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiClient {
    @GET("/api/cars")
    suspend fun getBrands():Response<List<Brand>>

    @GET("/api/cars/{brand}")
    suspend fun getMarks(@Path("brand") brand: String): Response<List<Model>>
}