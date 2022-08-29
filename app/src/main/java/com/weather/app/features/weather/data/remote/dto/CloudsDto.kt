package com.weather.app.features.weather.data.remote.dto

import com.weather.app.features.weather.domain.model.Clouds

data class CloudsDto(
    val all: Int
) {
    fun toClouds(): Clouds {
        return Clouds(
            all = all
        )
    }
}