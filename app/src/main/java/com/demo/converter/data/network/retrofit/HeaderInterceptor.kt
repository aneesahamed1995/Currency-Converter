package com.demo.converter.data.network.retrofit

import com.demo.converter.data.network.Header
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().apply { addHeader(Header.Key.AUTHORIZATION, Header.Value.API_KEY) }.build()
        return chain.proceed(request)
    }
}