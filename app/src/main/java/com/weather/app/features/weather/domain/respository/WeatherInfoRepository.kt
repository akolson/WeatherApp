package com.weather.app.features.weather.domain.respository

import com.weather.app.core.util.Resource
import com.weather.app.features.weather.domain.model.WeatherInfo
import kotlinx.coroutines.flow.Flow

interface WeatherInfoRepository {

    fun getWeatherInfo(lat: Double, long: Double): Flow<Resource<WeatherInfo>>
}