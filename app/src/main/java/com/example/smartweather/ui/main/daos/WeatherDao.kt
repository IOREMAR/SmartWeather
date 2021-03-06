package com.example.smartweather.ui.main.daos

import kotlinx.serialization.Serializable

@Serializable
data class WeatherDao(
    val current: Current,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int
) : java.io.Serializable