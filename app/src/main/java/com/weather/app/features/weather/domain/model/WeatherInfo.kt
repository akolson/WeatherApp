package com.weather.app.features.weather.domain.model

import com.weather.app.features.weather.data.local.entity.WeatherInfoEntity

data class WeatherInfo(
    val data: WeatherData,
    val forecast: WeatherForecast,
) {
    fun toWeatherInfoEntity(): WeatherInfoEntity {
        return WeatherInfoEntity(
            data = data,
            forecast = forecast
        )
    }
}
