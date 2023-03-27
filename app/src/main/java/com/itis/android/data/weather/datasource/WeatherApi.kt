package com.itis.android.data.weather.datasource

import com.itis.android.data.weather.datasource.response.MultipleWeatherResponse
import com.itis.android.data.weather.datasource.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String
    ): WeatherResponse

    @GET("weather")
    suspend fun getWeather(
        @Query("id") cityId: Int
    ): WeatherResponse

    @GET("find")
    suspend fun getNearestCitiesWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("cnt") count: Int = 10
    ): MultipleWeatherResponse

}
