package com.itis.android.data.weather

import com.itis.android.data.weather.datasource.WeatherApi
import com.itis.android.data.weather.mappers.toCityWeatherInfoList
import com.itis.android.data.weather.mappers.toDetailedWeatherInfo
import com.itis.android.data.weather.mappers.toWeatherInfo
import com.itis.android.domain.CityWeatherInfo
import com.itis.android.domain.DetailedWeatherInfo
import com.itis.android.domain.WeatherInfo
import com.itis.android.domain.WeatherRepository

class WeatherRepositoryImpl(
    private val api: WeatherApi
) : WeatherRepository {
    override suspend fun getWeather(query: String): WeatherInfo =
        api.getWeather(query).toWeatherInfo()

    override suspend fun getDetailedWeather(cityId: Int): DetailedWeatherInfo =
        api.getWeather(cityId).toDetailedWeatherInfo()

    override suspend fun getNearestCitiesWeather(
        latitude: Double,
        longitude: Double,
        count: Int
    ): List<CityWeatherInfo> =
        api.getNearestCitiesWeather(latitude, longitude, count)
            .toCityWeatherInfoList()
}
