package com.stetsiuk.weatherapi.models.onecall.common

data class Weather(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
)