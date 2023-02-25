package com.itis.android.ui.recyclerview

import android.content.Context
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.itis.android.R
import com.itis.android.databinding.ItemWeatherBinding

class WeatherHolder(
    private val binding: ItemWeatherBinding,
    private val context: Context,
    private val onClick: (WeatherUi) -> Unit
) : ViewHolder(binding.root) {
    fun onBind(weatherUi: WeatherUi) {
        binding.apply {
            tvCity.text = weatherUi.city
            tvTemp.text = context.getString(R.string.temp, weatherUi.temp)
            setTempColor(tvTemp, weatherUi.temp)
            ivIcon.load(
                "https://openweathermap.org/img/w/${weatherUi.icon}.png"
            )
            root.setOnClickListener { onClick(weatherUi) }
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
