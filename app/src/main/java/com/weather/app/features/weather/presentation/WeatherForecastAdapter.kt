package com.weather.app.features.weather.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weather.app.R
import com.weather.app.databinding.ListItemForecastBinding
import com.weather.app.features.weather.data.remote.WeatherInfoApi
import com.weather.app.features.weather.domain.model.WeatherData
import kotlinx.datetime.toLocalDate

class WeatherForecastAdapter(
    private var forecasts: List<WeatherData>
) : RecyclerView.Adapter<WeatherForecastAdapter.ForecastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val binding =
            ListItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            holder.bind(forecasts[position])
        }
    }

    override fun getItemCount(): Int {
        return forecasts.size
    }

    inner class ForecastViewHolder(private val binding: ListItemForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(weatherData: WeatherData) {
            binding.apply {
                val forecastDate = weatherData.dtTxt
                if (forecastDate != null) {
                    val forecastLocalDate = forecastDate.toLocalDate()
                    val dayOfWeek = forecastLocalDate.dayOfWeek.name
                    val displayDay = dayOfWeek.lowercase().replaceFirstChar(Char::titlecase)
                    textViewDay.text = displayDay
                }
                val icon = when (weatherData.weather?.getOrNull(0)?.main) {
                    WeatherInfoApi.WEATHER_CLOUDS -> R.drawable.ic_partly_sunny
                    WeatherInfoApi.WEATHER_CLEAR -> R.drawable.ic_clear
                    WeatherInfoApi.WEATHER_RAIN -> R.drawable.ic_rain
                    else -> null
                }
                if (icon != null) {
                    imageViewIcon.setBackgroundResource(icon)
                }
                layoutTemperature.textViewTemperature.text =
                    (weatherData.main?.temp ?: "-").toString()
            }
        }
    }
}