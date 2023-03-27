package com.itis.android.presentation.search.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.itis.android.databinding.ItemWeatherBinding
import com.itis.android.domain.weather.CityWeatherInfo

class WeatherAdapter(
    private val context: Context,
    private val onClick: (CityWeatherInfo) -> Unit
) : ListAdapter<CityWeatherInfo, WeatherHolder>(
    object : DiffUtil.ItemCallback<CityWeatherInfo>() {
        override fun areItemsTheSame(
            oldItem: CityWeatherInfo,
            newItem: CityWeatherInfo
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: CityWeatherInfo,
            newItem: CityWeatherInfo
        ): Boolean = oldItem == newItem

    }) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeatherHolder = WeatherHolder(
        ItemWeatherBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        context,
        onClick
    )

    override fun onBindViewHolder(
        holder: WeatherHolder,
        position: Int
    ) = holder.onBind(currentList[position])
}
