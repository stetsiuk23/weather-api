package com.stetsiuk.weatherapi.repository.net.onecall.model.daily

data class FeelsLike(
    val day: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)