package com.weather.app.features.weather.data.remote.dto

import com.weather.app.features.weather.domain.model.Wind

data class WindDto(
    val deg: Int,
    val speed: Double
) {
    fun toWind(): Wind {
        return Wind(
            deg = deg,
            speed = speed
        )
    }
}