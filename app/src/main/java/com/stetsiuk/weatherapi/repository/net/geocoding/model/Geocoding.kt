package com.stetsiuk.weatherapi.repository.net.geocoding.model

data class Geocoding(
    val name: String,
    val local_names: LocalNames?,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String?
)