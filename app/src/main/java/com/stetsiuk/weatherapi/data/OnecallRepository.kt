package com.stetsiuk.weatherapi.data

import com.stetsiuk.weatherapi.repository.net.Default
import com.stetsiuk.weatherapi.repository.net.MainData.apiKey
import com.stetsiuk.weatherapi.api.OneCallService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OnecallRepository @Inject constructor(private val oneCallService: OneCallService) {

    suspend fun get(
        lat: Double,
        lon: Double,
        units: String? = Default.units,
        lang: String? = Default.lang,
        appid: String = apiKey
    ) = withContext(Dispatchers.IO) { oneCallService.get(lat, lon, units, lang, appid) }
}