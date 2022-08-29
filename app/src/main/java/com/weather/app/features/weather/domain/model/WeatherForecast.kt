package com.weather.app.features.weather.domain.model

data class WeatherForecast(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherData>,
    val message: Int
)
