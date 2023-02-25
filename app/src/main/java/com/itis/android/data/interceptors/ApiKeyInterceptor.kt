package com.itis.android.data.interceptors

import com.itis.android.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val newUrl = original.url.newBuilder()
            .addQueryParameter("appid", BuildConfig.API_KEY)
            .build()
        return chain.proceed(
            original.newBuilder()
                .url(newUrl)
                .build()
        )
    }
}
