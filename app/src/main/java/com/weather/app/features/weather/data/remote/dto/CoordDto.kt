package com.weather.app.features.weather.data.remote.dto

import com.weather.app.features.weather.domain.model.Coord

data class CoordDto(
    val lat: Double,
    val lon: Double
) {
    fun toCoord(): Coord {
        return Coord(
            lat = lat,
            lon = lon
        )
    }
}