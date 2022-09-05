package com.weather.app.features.weather.data.remote.dto

import com.weather.app.core.util.Global
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
        val forecastsByDay = list.groupBy {
            Global.fromDateToLocalDateTime(it.dt_txt)?.dayOfWeek?.name
        }
        val forecasts = forecastsByDay.map { day ->
            day.value.maxBy { (it.main?.temp)!! }.toWeatherData()
        }.sortedBy { it.dt }.toMutableList()
        //Excludes the current day from forecasts
        forecasts.removeAt(0)
        return forecasts
    }
}