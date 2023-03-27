package com.itis.android.di

import android.content.Context
import com.itis.android.presentation.MainActivity
import com.itis.android.presentation.description.DescriptionFragment
import com.itis.android.presentation.search.SearchFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, NetworkModule::class, WeatherModule::class, DispatcherModule::class])
@Singleton
interface AppComponent {

    fun inject(weatherActivity: MainActivity)

    fun inject(searchFragment: SearchFragment)

    fun inject(descriptionFragment: DescriptionFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(applicationContext: Context): Builder

        fun build(): AppComponent
    }
}
