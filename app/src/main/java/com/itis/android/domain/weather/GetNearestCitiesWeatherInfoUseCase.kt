package com.itis.android.domain.weather

import com.itis.android.domain.geolocation.GeoLocation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetNearestCitiesWeatherInfoUseCase(
    private val repository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(
        geoLocation: GeoLocation,
        count: Int
    ) = withContext(dispatcher) {
        repository.getNearestCitiesWeather(geoLocation.lat, geoLocation.lon, count)
    }
}
