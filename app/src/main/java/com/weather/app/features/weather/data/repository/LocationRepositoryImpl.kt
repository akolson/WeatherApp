package com.weather.app.features.weather.data.repository

import com.weather.app.features.weather.data.remote.GoogleLocationDataSource
import com.weather.app.features.weather.domain.model.CustomLocation
import com.weather.app.features.weather.domain.respository.LocationRepository
import io.reactivex.rxjava3.core.Flowable

class LocationRepositoryImpl(
    private val googleLocationDataSource: GoogleLocationDataSource
) : LocationRepository {
    override fun getLocation(): Flowable<CustomLocation> {
        return googleLocationDataSource.locationObservable.map { it.toCustomLocation() }
    }
}