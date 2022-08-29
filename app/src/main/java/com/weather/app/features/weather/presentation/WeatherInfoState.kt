package com.weather.app.features.weather.presentation

import com.weather.app.features.weather.domain.model.WeatherInfo

data class WeatherInfoState(
    val weatherInfo: WeatherInfo?,
    val isLoading: Boolean = false
)
