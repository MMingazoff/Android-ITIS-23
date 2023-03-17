package com.itis.android.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetNearestCitiesWeatherInfoUseCase(
    private val repository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(
        latitude: Double,
        longitude: Double,
        count: Int
    ) = withContext(dispatcher) {
        repository.getNearestCitiesWeather(latitude, longitude, count)
    }
}
