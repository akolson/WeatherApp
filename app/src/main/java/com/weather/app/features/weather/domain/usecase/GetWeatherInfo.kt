package com.weather.app.features.weather.domain.usecase

import com.weather.app.core.util.Resource
import com.weather.app.features.weather.domain.model.WeatherInfo
import com.weather.app.features.weather.domain.respository.WeatherInfoRepository
import kotlinx.coroutines.flow.Flow


class GetWeatherInfo(
    private val repository: WeatherInfoRepository
) {
    operator fun invoke(lat: Double, long: Double): Flow<Resource<WeatherInfo>> {
        return repository.getWeatherInfo(lat, long)
    }
}