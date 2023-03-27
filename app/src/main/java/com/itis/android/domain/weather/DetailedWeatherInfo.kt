package com.itis.android.domain.weather

data class DetailedWeatherInfo(
    val name: String,
    val temp: Double,
    val description: String,
    val tempMax: Double,
    val tempMin: Double,
    val tempFeelsLike: Double,
    val humidity: Int,
    val windSpeed: Double,
    val windDegrees: Int,
    val pressure: Int,
    val icon: String
)
