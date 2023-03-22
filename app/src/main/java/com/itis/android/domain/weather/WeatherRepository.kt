package com.itis.android.domain.weather

interface WeatherRepository {
    suspend fun getWeather(query: String): WeatherInfo
    suspend fun getDetailedWeather(cityId: Int): DetailedWeatherInfo
    suspend fun getNearestCitiesWeather(
        latitude: Double,
        longitude: Double,
        count: Int
    ): List<CityWeatherInfo>
}
