package com.stetsiuk.weatherapi.repository.net.onecall.model.common

data class Weather(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
)