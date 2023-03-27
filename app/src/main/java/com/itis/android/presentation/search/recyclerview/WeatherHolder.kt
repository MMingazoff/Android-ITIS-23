package com.itis.android.presentation.search.recyclerview

import android.content.Context
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.itis.android.R
import com.itis.android.databinding.ItemWeatherBinding
import com.itis.android.domain.weather.CityWeatherInfo

class WeatherHolder(
    private val binding: ItemWeatherBinding,
    private val context: Context,
    private val onClick: (CityWeatherInfo) -> Unit
) : ViewHolder(binding.root) {
    fun onBind(weatherInfo: CityWeatherInfo) {
        binding.apply {
            tvCity.text = weatherInfo.name
            tvTemp.text = context.getString(R.string.temp, weatherInfo.temp)
            setTempColor(tvTemp, weatherInfo.temp)
            ivIcon.load(
                "https://openweathermap.org/img/w/${weatherInfo.weatherIcon}.png"
            )
            root.setOnClickListener { onClick(weatherInfo) }
        }
    }

    private fun setTempColor(textViewTemp: TextView, temp: Double) {
        @ColorRes val colorId = when (temp) {
            in Double.MIN_VALUE..-20.0 -> R.color.dark_blue
            in -20.0..-5.0 -> R.color.blue
            in -5.0..5.0 -> R.color.green
            in 5.0..15.0 -> R.color.orange
            else -> R.color.red
        }
        textViewTemp.setTextColor(context.getColor(colorId))
    }
}
