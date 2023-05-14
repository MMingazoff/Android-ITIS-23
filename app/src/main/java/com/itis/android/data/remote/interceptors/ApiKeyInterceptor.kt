package com.itis.android.data.remote.interceptors

import com.itis.android.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val headers = original.headers.newBuilder()
            .add("X-API-KEY", BuildConfig.API_KEY)
            .add("accept", "application/json")
            .build()
        return chain.proceed(
            original.newBuilder()
                .headers(headers)
                .build()
        )
    }
}
