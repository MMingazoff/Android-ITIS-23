package com.itis.android.di

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .context(applicationContext)
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}
