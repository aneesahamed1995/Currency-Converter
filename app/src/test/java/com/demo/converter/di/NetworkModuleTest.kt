package com.demo.converter.di

import com.demo.converter.data.network.NetworkStateManager
import com.demo.converter.utils.FakeNetworkStateManager
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val networkModuleTest = module {
    single { MockWebServer() }
    factory<NetworkStateManager> { FakeNetworkStateManager() }
    factory<HttpUrl>{ get<MockWebServer>().url("/") }
    factory<OkHttpClient> {
        provideOkHttpClient(
            get(InterceptorQualifier.httpLogging),
            get(InterceptorQualifier.header)
        )
    }
}

private fun provideOkHttpClient(
    httpLoggerInterceptor: Interceptor,
    headerInterceptor: Interceptor
) = OkHttpClient.Builder().apply {
    connectTimeout(10, TimeUnit.SECONDS)
    readTimeout(10, TimeUnit.SECONDS)
    writeTimeout(10, TimeUnit.SECONDS)
    addInterceptor(headerInterceptor)
    addInterceptor(httpLoggerInterceptor)
}.build()