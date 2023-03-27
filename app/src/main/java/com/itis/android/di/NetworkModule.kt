package com.itis.android.di

import com.itis.android.BuildConfig
import com.itis.android.data.interceptors.ApiKeyInterceptor
import com.itis.android.data.interceptors.MetricUnitsInterceptor
import com.itis.android.data.weather.datasource.WeatherApi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class InterceptApiKey

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class InterceptLogger

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class InterceptMetricUnits

@Module
class NetworkModule {

    @Provides
    @InterceptLogger
    fun provideLoggingInterceptor(): Interceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @InterceptApiKey
    fun provideApiKeyInterceptor(): Interceptor = ApiKeyInterceptor()

    @Provides
    @InterceptMetricUnits
    fun provideMetricUnitsInterceptor(): Interceptor = MetricUnitsInterceptor()

    @Provides
    fun provideHttpClient(
        @InterceptApiKey apiKeyInterceptor: Interceptor,
        @InterceptMetricUnits metricUnitsInterceptor: Interceptor,
        @InterceptLogger loggingInterceptor: Interceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor)
        .addInterceptor(metricUnitsInterceptor)
        .addInterceptor(loggingInterceptor)
        .connectTimeout(10L, TimeUnit.SECONDS)
        .build()

    @Provides
    fun provideRetrofit(
        httpClient: OkHttpClient,
        gsonFactory: GsonConverterFactory,
    ): Retrofit = Retrofit.Builder()
        .client(httpClient)
        .baseUrl(BuildConfig.API_ENDPOINT)
        .addConverterFactory(gsonFactory)
        .build()

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun provideWeatherApi(
        retrofit: Retrofit
    ): WeatherApi = retrofit.create(WeatherApi::class.java)

}
