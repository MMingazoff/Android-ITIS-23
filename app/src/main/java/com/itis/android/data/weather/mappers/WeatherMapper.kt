package com.itis.android.data.weather.mappers

import com.itis.android.data.weather.datasource.response.MultipleWeatherResponse
import com.itis.android.data.weather.datasource.response.WeatherResponse
import com.itis.android.domain.weather.CityWeatherInfo
import com.itis.android.domain.weather.DetailedWeatherInfo
import com.itis.android.domain.weather.WeatherInfo

fun WeatherResponse.toWeatherInfo() = WeatherInfo(
    id = id,
    city = name,
    temp = main.temp,
    icon = weather.first().icon
)

fun WeatherResponse.toDetailedWeatherInfo() = DetailedWeatherInfo(
    name = name,
    temp = main.temp,
    description = weather.first().description,
    tempMax = main.tempMax,
    tempMin = main.tempMin,
    tempFeelsLike = main.feelsLike,
    humidity = main.humidity,
    windSpeed = wind.speed,
    windDegrees = wind.deg,
    pressure = main.pressure,
    icon = weather.first().icon
)

fun MultipleWeatherResponse.toCityWeatherInfoList() = cities.map {
    CityWeatherInfo(
        id = it.id,
        temp = it.main.temp,
        name = it.name,
        weatherIcon = it.weather.first().icon
    )
}
