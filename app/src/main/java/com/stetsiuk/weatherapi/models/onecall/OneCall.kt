package com.stetsiuk.weatherapi.models.onecall

import com.stetsiuk.weatherapi.models.onecall.daily.Daily

data class OneCall(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int,
    val daily: List<Daily>,
)


