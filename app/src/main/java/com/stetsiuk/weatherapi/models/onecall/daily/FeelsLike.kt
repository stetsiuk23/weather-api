package com.stetsiuk.weatherapi.models.onecall.daily

data class FeelsLike(
    val day: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)