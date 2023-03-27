package com.itis.android.domain.weather

import com.itis.android.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetDetailedWeatherByCityIdUseCase @Inject constructor(
    private val repository: WeatherRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(cityId: Int) = withContext(dispatcher) {
        repository.getDetailedWeather(cityId)
    }
}
