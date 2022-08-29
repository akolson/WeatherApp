package com.weather.app.features.weather.data.local.entity

import com.weather.app.features.weather.domain.model.CustomLocation

data class LocationEntity(
    val lat: Double,
    val long: Double,
) {
    fun toCustomLocation(): CustomLocation {
        return CustomLocation(
            lat = lat,
            long = long
        )
    }
}
