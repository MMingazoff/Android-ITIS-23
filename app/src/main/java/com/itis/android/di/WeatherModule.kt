package com.itis.android.di

import com.itis.android.data.weather.WeatherRepositoryImpl
import com.itis.android.data.weather.datasource.WeatherApi
import com.itis.android.domain.weather.WeatherRepository
import dagger.Module
import dagger.Provides

@Module
class WeatherModule {

    @Provides
    fun provideWeatherRepository(
        weatherApi: WeatherApi
    ): WeatherRepository = WeatherRepositoryImpl(weatherApi)
}
