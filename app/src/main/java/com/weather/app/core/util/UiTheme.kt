package com.weather.app.core.util

import com.weather.app.R
import com.weather.app.features.weather.data.remote.WeatherInfoApi
import com.weather.app.features.weather.domain.model.WeatherTheme

class UiTheme {

    companion object {

        fun mapWeatherToTheme(weather: String): WeatherTheme {
            return when (weather) {
                WeatherInfoApi.WEATHER_CLOUDS -> WeatherTheme(
                    label = "CLOUDY",
                    color = R.color.cloudy,
                    background = R.drawable.forest_cloudy
                )
                WeatherInfoApi.WEATHER_CLEAR -> WeatherTheme(
                    label = "SUNNY",
                    color = R.color.sunny,
                    background = R.drawable.forest_sunny
                )
                WeatherInfoApi.WEATHER_RAIN -> WeatherTheme(
                    label = "RAINY",
                    color = R.color.rainy,
                    background = R.drawable.forest_rainy
                )
                else -> WeatherTheme(
                    label = "UNKNOWN",
                    color = null,
                    background = null
                )
            }
        }
    }
}