package com.weather.app.features.weather.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.weather.app.features.weather.data.local.entity.WeatherInfoEntity

@Dao
interface WeatherInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherInfo(info: WeatherInfoEntity)

    @Query("DELETE FROM weatherinfoentity")
    suspend fun deleteWeatherInfo()

    @Query("SELECT * FROM weatherinfoentity ORDER BY id DESC LIMIT 1")
    suspend fun getLastWeatherInfo(): WeatherInfoEntity
}