package com.example.smartweather.ui.main.daos

data class Hourly(
    val clouds: Int,
    val dew_point: Double,
    val dt: Int,
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int,
    val rain: Rain,
    val temp: Double,
    val uvi: Int,
    val visibility: Int,
    val weather: List<WeatherX>,
    val wind_deg: Int,
    val wind_gust: Double,
    val wind_speed: Double
)