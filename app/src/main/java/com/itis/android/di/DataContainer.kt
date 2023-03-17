package com.itis.android.di

import com.itis.android.BuildConfig
import com.itis.android.data.interceptors.ApiKeyInterceptor
import com.itis.android.data.interceptors.MetricUnitsInterceptor
import com.itis.android.data.weather.WeatherRepositoryImpl
import com.itis.android.data.weather.datasource.WeatherApi
import com.itis.android.domain.GetDetailedWeatherByCityIdUseCase
import com.itis.android.domain.GetNearestCitiesWeatherInfoUseCase
import com.itis.android.domain.GetWeatherByCityNameUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DataContainer {
    private const val BASE_URL = BuildConfig.API_ENDPOINT

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    private val httClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(ApiKeyInterceptor())
            .addInterceptor(MetricUnitsInterceptor())
            .connectTimeout(10L, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .client(httClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val weatherApi: WeatherApi = retrofit.create(WeatherApi::class.java)

    private val weatherRepository = WeatherRepositoryImpl(weatherApi)

    val getWeatherByCityNameUseCase
        get() = GetWeatherByCityNameUseCase(weatherRepository)
    val getDetailedWeatherByCityIdUseCase
        get() = GetDetailedWeatherByCityIdUseCase(weatherRepository)
    val getNearestCitiesWeatherInfoUseCase
        get() = GetNearestCitiesWeatherInfoUseCase(weatherRepository)
}
