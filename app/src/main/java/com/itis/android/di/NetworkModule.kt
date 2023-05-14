package com.itis.android.di

import com.itis.android.BuildConfig
import com.itis.android.data.remote.datasource.KinopoiskApi
import com.itis.android.data.remote.interceptors.ApiKeyInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    single {
        MoshiConverterFactory.create(
            Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
        )
    }

    val apiKeyQualifier = StringQualifier("apiKey")

    val loggerQualifier = StringQualifier("logger")

    factory<Interceptor>(apiKeyQualifier) { ApiKeyInterceptor() }
    factory<Interceptor>(loggerQualifier) {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }
    single {
        provideHttpClient(
            apiKeyInterceptor = get(apiKeyQualifier),
            loggingInterceptor = get(loggerQualifier),
        )
    }
    single {
        provideRetrofit(
            httpClient = get(),
            moshiFactory = get(),
        )
    }
    single {
        get<Retrofit>().create(KinopoiskApi::class.java)
    }
}

private fun provideHttpClient(
    apiKeyInterceptor: Interceptor,
    loggingInterceptor: Interceptor,
): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .addInterceptor(apiKeyInterceptor)
    .build()

private fun provideRetrofit(
    httpClient: OkHttpClient,
    moshiFactory: MoshiConverterFactory,
): Retrofit = Retrofit.Builder()
    .client(httpClient)
    .baseUrl(BuildConfig.API_ENDPOINT)
    .addConverterFactory(moshiFactory)
    .build()
