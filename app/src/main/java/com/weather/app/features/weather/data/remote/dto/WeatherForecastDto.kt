package com.weather.app.features.weather.data.remote.dto

import com.weather.app.features.weather.domain.model.WeatherData
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
            list = getSingleForecastPerDay(),
            message = message
        )
    }

    private fun getSingleForecastPerDay(): List<WeatherData> {
        val list = list.map {
            it.dt_txt = it.dt_txt?.split(" ")?.getOrNull(0)
            it.toWeatherData()
        }.distinctBy { it.dtTxt }.sortedBy { it.dt }.toMutableList()
        //Excludes the current day from forecasts
        list.removeAt(0)
        return list
    }
}