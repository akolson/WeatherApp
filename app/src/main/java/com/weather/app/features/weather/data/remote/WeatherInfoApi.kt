package com.weather.app.features.weather.data.remote

import com.weather.app.features.weather.data.remote.dto.WeatherDataDto
import com.weather.app.features.weather.data.remote.dto.WeatherForecastDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherInfoApi {

    @GET("weather")
    suspend fun getWeather(
        @Query("lat") lat: String,
        @Query("lon") long: String,
    ): WeatherDataDto

    @GET("forecast")
    suspend fun getWeatherForecast(
        @Query("lat") lat: String,
        @Query("lon") long: String
    ): WeatherForecastDto

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val API_KEY =
            "f7da06e9405186f5a03a9a5580529c9c"//NOTE: This should not be stored here for production ready apps
        const val UNIT_METRIC = "metric"
        const val WEATHER_CLEAR = "Clear"
        const val WEATHER_CLOUDS = "Clouds"
        const val WEATHER_RAIN = "Rain"
    }
}