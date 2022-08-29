package com.weather.app.features.weather.domain.usecase

import com.weather.app.features.weather.domain.model.CustomLocation
import com.weather.app.features.weather.domain.respository.LocationRepository
import io.reactivex.rxjava3.core.Flowable

class GetLocation(
    private val repository: LocationRepository
) {
    operator fun invoke(): Flowable<CustomLocation> {
        return repository.getLocation()
    }
}