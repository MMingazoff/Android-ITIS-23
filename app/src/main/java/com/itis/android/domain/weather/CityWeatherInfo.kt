package com.itis.android.domain.weather

data class CityWeatherInfo(
    val id: Int,
    val temp: Double,
    val name: String,
    val weatherIcon: String,
)
