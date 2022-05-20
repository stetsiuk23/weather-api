package com.stetsiuk.weatherapi.repository.net.onecall.model

import com.stetsiuk.weatherapi.repository.net.onecall.model.daily.Daily

data class OneCall(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int,
    val daily: List<Daily>,
)


