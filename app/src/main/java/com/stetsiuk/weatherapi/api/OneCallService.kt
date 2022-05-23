package com.stetsiuk.weatherapi.api

import com.stetsiuk.weatherapi.models.onecall.OneCall
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OneCallService {

    @GET("/data/2.5/onecall")
    suspend fun get(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String? = null,
        @Query("lang") lang: String? = null,
        @Query("appid") appid: String
    ): Response<OneCall>
}