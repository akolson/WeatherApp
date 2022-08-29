package com.weather.app.features.weather.domain.respository

import com.weather.app.features.weather.domain.model.CustomLocation
import io.reactivex.rxjava3.core.Flowable

interface LocationRepository {

    fun getLocation(): Flowable<CustomLocation>
}