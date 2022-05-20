package com.stetsiuk.weatherapi.repository.net.onecall.model.daily

data class Temp(
    val day: Double,
    val min: Double,
    val max: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)