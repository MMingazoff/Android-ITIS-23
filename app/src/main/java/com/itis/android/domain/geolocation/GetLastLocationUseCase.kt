package com.itis.android.domain.geolocation

import com.itis.android.data.geolocation.GeoLocationDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetLastLocationUseCase(
    private val dataSource: GeoLocationDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke() = withContext(dispatcher) {
        dataSource.getLastLocation()
    }
}
