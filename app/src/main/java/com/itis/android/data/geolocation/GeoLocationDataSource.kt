package com.itis.android.data.geolocation

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.itis.android.domain.geolocation.GeoLocation
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GeoLocationDataSource @Inject constructor(
    private val client: FusedLocationProviderClient
) {
    @SuppressLint("MissingPermission")
    suspend fun getLastLocation(): GeoLocation? = client.lastLocation.await()?.let {
        GeoLocation(
            lon = it.longitude,
            lat = it.latitude
        )
    }

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): GeoLocation =
        client.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, null).await().let {
            GeoLocation(
                lon = it.longitude,
                lat = it.latitude
            )
        }
}
