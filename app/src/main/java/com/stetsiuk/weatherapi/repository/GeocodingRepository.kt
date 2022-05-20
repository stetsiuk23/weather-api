package com.stetsiuk.weatherapi.repository

import com.stetsiuk.weatherapi.repository.net.APIKey
import com.stetsiuk.weatherapi.repository.net.geocoding.services.GeocodingService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GeocodingRepository @Inject constructor(private val geocodingService: GeocodingService) {
    suspend fun getByName(
        name: String,
        limit: Long? = 3,
        appid: String = APIKey) = withContext(Dispatchers.IO){ geocodingService.getByName(name, limit, appid)}

    suspend fun getByZip(
        zipCode: Long,
        appid: String = APIKey) = withContext(Dispatchers.IO){geocodingService.getByZip(zipCode, appid)}


    suspend fun getByCoordinates(
        lat: Double,
        lon: Double,
        limit: Long? = 3,
        appid: String = APIKey) = withContext(Dispatchers.IO){geocodingService.getByCoordinates(lat, lon, limit, appid)}
}