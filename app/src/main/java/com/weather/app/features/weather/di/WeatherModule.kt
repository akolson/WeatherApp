package com.weather.app.features.weather.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.weather.app.features.weather.data.local.Converters
import com.weather.app.features.weather.data.local.WeatherInfoDatabase
import com.weather.app.features.weather.data.remote.GoogleLocationDataSource
import com.weather.app.features.weather.data.remote.WeatherInfoApi
import com.weather.app.features.weather.data.repository.LocationRepositoryImpl
import com.weather.app.features.weather.data.repository.WeatherInfoRepositoryImpl
import com.weather.app.features.weather.data.util.GsonParser
import com.weather.app.features.weather.domain.respository.LocationRepository
import com.weather.app.features.weather.domain.respository.WeatherInfoRepository
import com.weather.app.features.weather.domain.usecase.GetLocation
import com.weather.app.features.weather.domain.usecase.GetWeatherInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {

    @Provides
    @Singleton
    fun provideGetWeatherInfo(repository: WeatherInfoRepository): GetWeatherInfo {
        return GetWeatherInfo(repository)
    }

    @Provides
    @Singleton
    fun provideGetLocation(repository: LocationRepository): GetLocation {
        return GetLocation(repository)
    }

    @Provides
    @Singleton
    fun provideWeatherInfoRepository(
        api: WeatherInfoApi,
        database: WeatherInfoDatabase,
    ): WeatherInfoRepository {
        return WeatherInfoRepositoryImpl(api, database.dao)
    }

    @Provides
    @Singleton
    fun provideLocationRepository(location: GoogleLocationDataSource): LocationRepository {
        return LocationRepositoryImpl(location)
    }

    @Provides
    @Singleton
    fun provideGoogleLocationDataSource(app: Application): GoogleLocationDataSource {
        return GoogleLocationDataSource(app)
    }

    @Provides
    @Singleton
    fun provideWeatherInfoDatabase(app: Application): WeatherInfoDatabase {
        return Room.databaseBuilder(
            app,
            WeatherInfoDatabase::class.java,
            "weather_app_db"
        ).addTypeConverter(Converters(GsonParser(Gson()))).build()
    }

    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val originalUrl = originalRequest.url

            val modifiedUrl = originalUrl
                .newBuilder()
                .addQueryParameter("appid", WeatherInfoApi.API_KEY)
                .addQueryParameter("units", WeatherInfoApi.UNIT_METRIC)
                .build()

            val requestBuilder = originalRequest
                .newBuilder()
                .url(modifiedUrl)
            val request = requestBuilder.build();
            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherInfoApi(
        okHttpClient: OkHttpClient
    ): WeatherInfoApi {
        return Retrofit.Builder()
            .baseUrl(WeatherInfoApi.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherInfoApi::class.java)
    }
}