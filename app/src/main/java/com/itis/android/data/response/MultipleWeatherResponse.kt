package com.itis.android.data.response

import com.google.gson.annotations.SerializedName

data class MultipleWeatherResponse(
    @SerializedName("cod")
    val cod: String,
    @SerializedName("count")
    val count: Int,
    @SerializedName("list")
    val cities: List<CityWeather>,
    @SerializedName("message")
    val message: String
)

data class Temp(
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    @SerializedName("temp_min")
    val tempMin: Double
)

data class CityWeather(
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: Temp,
    @SerializedName("name")
    val name: String,
    @SerializedName("weather")
    val weather: List<WeatherDescription>,
)

data class WeatherDescription(
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String
)
