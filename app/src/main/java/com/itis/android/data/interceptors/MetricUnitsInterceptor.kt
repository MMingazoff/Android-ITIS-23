package com.itis.android.data.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class MetricUnitsInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val newUrl = original.url.newBuilder()
            .addQueryParameter("units", "metric")
            .build()
        return chain.proceed(
            original.newBuilder()
                .url(newUrl)
                .build()
        )
    }
}
