package com.itis.android.domain.weather

data class WeatherInfo(
    val id: Int,
    val city: String,
    val temp: Double,
    val icon: String
)
