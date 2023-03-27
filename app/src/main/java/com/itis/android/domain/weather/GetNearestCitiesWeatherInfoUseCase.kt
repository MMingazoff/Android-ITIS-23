package com.itis.android.domain.weather

import com.itis.android.di.IoDispatcher
import com.itis.android.domain.geolocation.GeoLocation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetNearestCitiesWeatherInfoUseCase @Inject constructor(
    private val repository: WeatherRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(
        geoLocation: GeoLocation,
        count: Int
    ) = withContext(dispatcher) {
        repository.getNearestCitiesWeather(geoLocation.lat, geoLocation.lon, count)
    }
}
