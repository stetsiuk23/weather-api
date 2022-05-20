package com.stetsiuk.weatherapi.repository

import com.stetsiuk.weatherapi.repository.net.APIKey
import com.stetsiuk.weatherapi.repository.net.onecall.services.OneCallService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OnecallRepository @Inject constructor(private val oneCallService: OneCallService) {
    suspend fun get(
        lat: Double,
        lon: Double,
        units: String? = null,
        lang: String? = null,
        exclude: String? = null,
        appid: String = APIKey) =
        withContext(Dispatchers.IO){ oneCallService.get(lat, lon, units, lang, exclude, appid)}
}