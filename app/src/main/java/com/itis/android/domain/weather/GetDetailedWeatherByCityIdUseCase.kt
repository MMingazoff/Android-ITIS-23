package com.itis.android.domain.weather

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetDetailedWeatherByCityIdUseCase(
    private val repository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(cityId: Int) = withContext(dispatcher) {
        repository.getDetailedWeather(cityId)
    }
}
