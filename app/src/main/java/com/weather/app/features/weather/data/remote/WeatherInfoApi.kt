package com.weather.app.features.weather.data.remote

import com.weather.app.features.weather.data.remote.dto.WeatherDataDto
import com.weather.app.features.weather.data.remote.dto.WeatherForecastDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherInfoApi {

    @GET("/weather")
    suspend fun getWeather(
        @Query("lat") lat: String,
        @Query("lon") long: String,
    ): WeatherDataDto

    @GET("/forecast")
    suspend fun getWeatherForecast(
        @Query("lat") lat: String,
        @Query("lon") long: String,
    ): WeatherForecastDto

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5"
        const val API_KEY = ""//NOTE: This should not be stored here for production ready apps
    }
}