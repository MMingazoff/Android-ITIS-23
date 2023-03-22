package com.itis.android.di

import android.app.Application
import com.itis.android.data.geolocation.GeoLocationDataSource
import com.itis.android.domain.geolocation.GetCurrentLocationUseCase
import com.itis.android.domain.geolocation.GetLastLocationUseCase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        GeoLocationDataSource(DataContainer.getFusedLocationClient(applicationContext)).let {
            getLastLocationUseCase = GetLastLocationUseCase(it)
            getCurrentLocationUseCase = GetCurrentLocationUseCase(it)
        }
    }

    companion object {
        lateinit var getLastLocationUseCase: GetLastLocationUseCase
        lateinit var getCurrentLocationUseCase: GetCurrentLocationUseCase
    }
}
