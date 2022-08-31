package com.weather.app.features.weather.data.repository

import com.weather.app.core.util.Resource
import com.weather.app.features.weather.data.local.WeatherInfoDao
import com.weather.app.features.weather.data.local.entity.WeatherInfoEntity
import com.weather.app.features.weather.data.remote.WeatherInfoApi
import com.weather.app.features.weather.domain.model.WeatherInfo
import com.weather.app.features.weather.domain.respository.WeatherInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class WeatherInfoRepositoryImpl(
    private val api: WeatherInfoApi,
    private val dao: WeatherInfoDao
) : WeatherInfoRepository {
    override fun getWeatherInfo(lat: Double, long: Double): Flow<Resource<WeatherInfo>> = flow {
        emit(Resource.Loading())

        val localWeatherInfo = dao.getLastWeatherInfo() as WeatherInfoEntity?
        emit(Resource.Loading(data = localWeatherInfo?.toWeatherInfo()))

        try {
            val remoteWeather = api.getWeather(lat.toString(), long.toString())
            val remoteForeCast = api.getWeatherForecast(lat.toString(), long.toString())
            val remoteWeatherInfo = WeatherInfo(
                data = remoteWeather.toWeatherData(),
                forecast = remoteForeCast.toWeatherForecast()
            )

            dao.deleteWeatherInfo()
            dao.insertWeatherInfo(remoteWeatherInfo.toWeatherInfoEntity())
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong!",
                    data = localWeatherInfo?.toWeatherInfo()
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection",
                    data = localWeatherInfo?.toWeatherInfo()
                )
            )
        }

        val newWeatherInfo = dao.getLastWeatherInfo() as WeatherInfoEntity?
        emit(Resource.Success(newWeatherInfo?.toWeatherInfo()))
    }
}