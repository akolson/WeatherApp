package com.weather.app.features.weather.data.remote.dto

import com.weather.app.features.weather.domain.model.Weather

data class WeatherDto(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
) {
    fun toWeather(): Weather {
        return Weather(
            description = description,
            icon = icon,
            id = id,
            main = main,
        )
    }
}