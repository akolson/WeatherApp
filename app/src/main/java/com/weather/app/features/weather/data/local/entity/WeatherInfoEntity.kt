package com.weather.app.features.weather.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.weather.app.features.weather.domain.model.WeatherData
import com.weather.app.features.weather.domain.model.WeatherForecast
import com.weather.app.features.weather.domain.model.WeatherInfo

@Entity
data class WeatherInfoEntity(
    val data: WeatherData,
    val forecast: WeatherForecast,
    @PrimaryKey val id: Int? = null
) {
    fun toWeatherInfo(): WeatherInfo {
        return WeatherInfo(
            data = data,
            forecast = forecast,
        )
    }
}