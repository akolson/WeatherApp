package com.weather.app.features.weather.data.remote.dto

import com.weather.app.features.weather.domain.model.City

data class CityDto(
    val coord: CoordDto,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
) {
    fun toCity(): City {
        return City(
            coord = coord.toCoord(),
            country = country,
            id = id,
            name = name,
            population = population,
            sunrise = sunrise,
            sunset = sunset,
            timezone = timezone,
        )
    }
}