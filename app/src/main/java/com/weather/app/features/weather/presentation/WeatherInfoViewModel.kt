package com.weather.app.features.weather.presentation

import android.content.pm.PackageManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.app.core.util.Resource
import com.weather.app.features.weather.domain.usecase.GetLocation
import com.weather.app.features.weather.domain.usecase.GetWeatherInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherInfoViewModel @Inject constructor(
    private val getWeatherInfo: GetWeatherInfo,
    private val getLocation: GetLocation,
) : ViewModel() {

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private val _weatherInfoState = MutableLiveData<WeatherInfoState>()
    val weatherInfoState: LiveData<WeatherInfoState> = _weatherInfoState

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onRequestPermissionResult(requestCode: Int, grantResults: IntArray) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty()) {
            when (grantResults.first()) {
                PackageManager.PERMISSION_GRANTED -> onLocationPermissionGranted()
                PackageManager.PERMISSION_DENIED -> onLocationPermissionDenied()
            }
        }
    }

    fun onLocationPermissionGranted() {
        getLocation().observeOn(Schedulers.computation())
            .subscribe({ location ->
                loadWeatherInfo(
                    lat = location.lat,
                    long = location.long
                )
            }, {
                showSnackbar("This app requires location permission to work")
            })
    }

    private fun loadWeatherInfo(lat: Double, long: Double) = viewModelScope.launch {
        getWeatherInfo(lat, long).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _weatherInfoState.postValue(
                        _weatherInfoState.value?.copy(
                            weatherInfo = result.data,
                            isLoading = false
                        )
                    )
                }
                is Resource.Error -> {
                    _weatherInfoState.postValue(
                        _weatherInfoState.value?.copy(
                            weatherInfo = result.data,
                            isLoading = false
                        )
                    )
                    showSnackbar(result.message ?: "Unknown error")
                }
                is Resource.Loading -> {
                    _weatherInfoState.postValue(
                        _weatherInfoState.value?.copy(
                            weatherInfo = result.data,
                            isLoading = true
                        )
                    )
                }
            }
        }.launchIn(this)
    }

    fun onLocationPermissionDenied() {
        showSnackbar("This app requires location permission to work")
    }

    private fun showSnackbar(message: String) = viewModelScope.launch {
        _eventFlow.emit(UIEvent.ShowSnackbar(message))
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
    }
}