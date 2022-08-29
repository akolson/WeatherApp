package com.weather.app.features.weather.data.remote.dto

import com.weather.app.features.weather.domain.model.Main

data class MainDto(
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
) {
    fun toMain(): Main {
        return Main(
            feels_like = feels_like,
            humidity = humidity,
            pressure = pressure,
            temp = temp,
            temp_max = temp_max,
            temp_min = temp_min
        )
    }
}