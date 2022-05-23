package com.stetsiuk.weatherapi.data

import com.stetsiuk.weatherapi.repository.net.Default
import com.stetsiuk.weatherapi.repository.net.MainData.apiKey
import com.stetsiuk.weatherapi.api.GeocodingService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GeocodingRepository @Inject constructor(private val geocodingService: GeocodingService) {

    suspend fun getByName(
        name: String,
        limit: Long? = Default.limit,
        appid: String = apiKey
    ) = withContext(Dispatchers.IO){ geocodingService.getByName(name, limit, appid) }

    suspend fun getByCoordinates(
        lat: Double,
        lon: Double,
        limit: Long? = Default.limit,
        appid: String = apiKey
    ) = withContext(Dispatchers.IO){ geocodingService.getByCoordinates(lat, lon, limit, appid) }
}