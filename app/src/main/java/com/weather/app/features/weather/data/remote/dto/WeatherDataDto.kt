package com.weather.app.features.weather.data.remote.dto

import com.weather.app.features.weather.domain.model.WeatherData

data class WeatherDataDto(
    val base: String,
    val clouds: CloudsDto,
    val cod: Int,
    val coord: CoordDto,
    val dt: Int,
    val id: Int,
    val main: MainDto,
    val name: String,
    val sys: SysDto,
    val timezone: Int,
    val visibility: Int,
    val weather: List<WeatherDto>,
    val wind: WindDto
) {
    fun toWeatherData(): WeatherData {
        return WeatherData(
            base = base,
            clouds = clouds.toClouds(),
            cod = cod,
            coord = coord.toCoord(),
            dt = dt,
            id = id,
            main = main.toMain(),
            name = name,
            sys = sys.toSys(),
            timezone = timezone,
            visibility = visibility,
            weather = weather.map { it.toWeather() },
            wind = wind.toWind()
        )
    }
}