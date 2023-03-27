package com.itis.android.domain.geolocation

import com.itis.android.data.geolocation.GeoLocationDataSource
import com.itis.android.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCurrentLocationUseCase @Inject constructor(
    private val dataSource: GeoLocationDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke() = withContext(dispatcher) {
        dataSource.getCurrentLocation()
    }
}
