package com.itis.android.ui.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.itis.android.databinding.ItemWeatherBinding

class WeatherAdapter(
    private val list: List<WeatherUi>,
    private val context: Context,
    private val onClick: (WeatherUi) -> Unit
): Adapter<WeatherHolder>() {
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

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(
        holder: WeatherHolder,
        position: Int
    ) = holder.onBind(list[position])
}
