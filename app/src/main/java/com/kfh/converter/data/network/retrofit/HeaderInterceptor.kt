package com.kfh.converter.data.network.retrofit

import com.kfh.converter.data.network.Header
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().apply { addHeader(Header.Key.API_KEY, Header.Value.API_KEY) }.build()
        return chain.proceed(request)
    }
}