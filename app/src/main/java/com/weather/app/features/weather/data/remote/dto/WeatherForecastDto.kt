package com.weather.app.features.weather.data.remote.dto

import com.weather.app.features.weather.domain.model.WeatherForecast

data class WeatherForecastDto(
    val city: CityDto,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherDataDto>,
    val message: Int
) {
    fun toWeatherForecast(): WeatherForecast {
        return WeatherForecast(
            city = city.toCity(),
            cnt = cnt,
            cod = cod,
            list = list.map { it.toWeatherData() },
            message = message
        )
    }
}