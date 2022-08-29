package com.weather.app.features.weather.data.remote.dto

import com.weather.app.features.weather.domain.model.Sys

data class SysDto(
    val country: String,
    val id: Int,
    val message: Double,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
) {
    fun toSys(): Sys {
        return Sys(
            country = country,
            id = id,
            message = message,
            sunrise = sunrise,
            sunset = sunset,
            type = type
        )
    }
}