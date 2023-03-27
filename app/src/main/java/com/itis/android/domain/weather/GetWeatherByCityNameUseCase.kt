package com.itis.android.domain.weather

import com.itis.android.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetWeatherByCityNameUseCase @Inject constructor(
    private val repository: WeatherRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(query: String) = withContext(dispatcher) {
        repository.getWeather(query)
    }
}
