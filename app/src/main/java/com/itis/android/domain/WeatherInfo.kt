package com.itis.android.domain

data class WeatherInfo(
    val id: Int,
    val city: String,
    val temp: Double,
    val icon: String
)
