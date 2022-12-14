package com.weather.app.features.weather.data.remote

import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.weather.app.features.weather.data.local.entity.LocationEntity
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

private const val LOCATION_REQUEST_INTERVAL = 10000L
private const val LOCATION_REQUEST_FASTEST_INTERVAL = 5000L

class GoogleLocationDataSource @Inject constructor(context: Context) {

    private val locationSubject = PublishSubject.create<LocationEntity>()
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationRequest: LocationRequest = LocationRequest.create().apply {
        interval = LOCATION_REQUEST_INTERVAL
        fastestInterval = LOCATION_REQUEST_FASTEST_INTERVAL
        //priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        // Use this option whenever uses the emulator, that's the only way to force the emulator
        // GPS to be activated
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.locations.forEach(::setLocation)
        }
    }

    val locationObservable: Flowable<LocationEntity> =
        locationSubject.toFlowable(BackpressureStrategy.MISSING)
            .doOnSubscribe { startLocationUpdates() }
            .doOnCancel { stopLocationUpdates() }

    private fun startLocationUpdates() {
        fusedLocationClient.lastLocation.addOnSuccessListener(::setLocation)
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun setLocation(location: Location?) {
        locationSubject.onNext(
            LocationEntity(
                lat = location?.latitude ?: 0.0,
                long = location?.longitude ?: 0.0
            )
        )
    }
}