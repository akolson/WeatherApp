package com.weather.app

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.weather.app.core.util.UiTheme
import com.weather.app.databinding.ActivityMainBinding
import com.weather.app.features.weather.presentation.WeatherForecastAdapter
import com.weather.app.features.weather.presentation.WeatherInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: WeatherInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.weatherInfoState.observe(this) { weatherStatInfo ->
            Log.e("weatherStatInfo", Gson().toJson(weatherStatInfo))
            binding.apply {
                val weatherInfo = weatherStatInfo?.weatherInfo
                val weatherData = weatherInfo?.data
                val weatherForecast = weatherInfo?.forecast

                weatherData?.weather?.forEach {
                    val weather = UiTheme.mapWeatherToTheme(it.main)
                    if (weather.color != null) {
                        mainContent.setBackgroundColor(
                            ContextCompat.getColor(
                                this@MainActivity,
                                weather.color
                            )
                        )
                    }
                    if (weather.background != null) {
                        appBar.setBackgroundResource(weather.background)
                    }
                    textViewWeather.text = weather.label
                }

                val temperature = weatherData?.main
                val currentTemperature = (temperature?.temp ?: "-").toString()
                textViewTemperature.text = currentTemperature
                layoutCurrentWeather.apply {
                    layoutMin.textViewTemperature.text = (temperature?.temp_min ?: "-").toString()
                    layoutCurrent.textViewTemperature.text = currentTemperature
                    layoutMax.textViewTemperature.text = (temperature?.temp_max ?: "-").toString()
                }
                recyclerViewForecast.apply {
                    setHasFixedSize(true)
                    adapter = WeatherForecastAdapter(weatherForecast?.list ?: listOf())
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.eventFlow.collect { event ->
                when (event) {
                    is WeatherInfoViewModel.UIEvent.ShowSnackbar -> {
                        Snackbar.make(binding.root, event.message, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }

        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    viewModel.onLocationPermissionGranted()
                } else {
                    viewModel.onLocationPermissionDenied()
                }
            }

        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                viewModel.onLocationPermissionGranted()
            }
            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        viewModel.onRequestPermissionResult(requestCode, grantResults)
    }
}