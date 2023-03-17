package com.itis.android.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetWeatherByCityNameUseCase(
    private val repository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(query: String) = withContext(dispatcher) {
        repository.getWeather(query)
    }
}
