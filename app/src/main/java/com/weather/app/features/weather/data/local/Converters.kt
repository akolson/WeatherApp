package com.weather.app.features.weather.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.weather.app.features.weather.data.util.JsonParser
import com.weather.app.features.weather.domain.model.WeatherData
import com.weather.app.features.weather.domain.model.WeatherForecast

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun fromWeatherData(json: String): WeatherData? {
        return jsonParser.fromJson<WeatherData>(
            json,
            WeatherData::class.java
        )
    }

    @TypeConverter
    fun toWeatherDataJson(data: WeatherData): String? {
        return jsonParser.toJson(
            data,
            WeatherData::class.java
        )
    }

    @TypeConverter
    fun fromWeatherForecast(json: String): WeatherForecast? {
        return jsonParser.fromJson<WeatherForecast>(
            json,
            WeatherForecast::class.java
        )
    }

    @TypeConverter
    fun toWeatherForecastJson(forecast: WeatherForecast): String? {
        return jsonParser.toJson(
            forecast,
            WeatherForecast::class.java
        )
    }
}