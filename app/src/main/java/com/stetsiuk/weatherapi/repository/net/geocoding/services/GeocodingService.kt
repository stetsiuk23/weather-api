package com.stetsiuk.weatherapi.repository.net.geocoding.services

import com.stetsiuk.weatherapi.repository.net.geocoding.model.Geocoding
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingService {
    @GET(value = "/geo/1.0/direct")
    suspend fun getByName(
        @Query("q") city_name: String,
        @Query("limit") limit: Long? = null,
        @Query("appid") appid: String
    ): Response<List<Geocoding>>

    @GET("/geo/1.0/zip")
    suspend fun getByZip(
        @Query("zip") zip_code: Long,
        @Query("appid") appid: String
    ): Response<List<Geocoding>>

    @GET("/geo/1.0/reverse")
    suspend fun getByCoordinates(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("limit") limit: Long? = null,
        @Query("appid") appid: String
    ): Response<List<Geocoding>>
}