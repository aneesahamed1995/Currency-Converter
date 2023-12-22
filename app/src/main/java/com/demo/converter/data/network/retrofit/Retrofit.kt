package com.demo.converter.data.network.retrofit

import android.os.Build
import com.datatheorem.android.trustkit.pinning.OkHttp3Helper
import com.demo.converter.BuildConfig
import com.demo.converter.data.network.NetworkStateManager
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


fun provideOkHttpClient(
    httpLoggerInterceptor: Interceptor,
    headerInterceptor: Interceptor
) = OkHttpClient.Builder().apply {
    connectTimeout(30, TimeUnit.SECONDS)
    readTimeout(30, TimeUnit.SECONDS)
    writeTimeout(30, TimeUnit.SECONDS)
    addInterceptor(headerInterceptor)
    addInterceptor(httpLoggerInterceptor)
    if (Build.VERSION.SDK_INT< Build.VERSION_CODES.N){
        sslSocketFactory(OkHttp3Helper.getSSLSocketFactory(), OkHttp3Helper.getTrustManager())
        addInterceptor(OkHttp3Helper.getPinningInterceptor())
        followRedirects(false)
        followSslRedirects(false)
    }
}.build()

fun provideRetrofit(
    baseUrl: HttpUrl,
    okHttpClient: OkHttpClient,
    callAdapterFactory: CallAdapter.Factory,
    convertAdapterFactory: Converter.Factory
) = Retrofit.Builder().apply {
    baseUrl(baseUrl).build()
    client(okHttpClient)
    addCallAdapterFactory(callAdapterFactory)
    addConverterFactory(convertAdapterFactory)
}.build()

fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
    level = if(BuildConfig.DEBUG){
        HttpLoggingInterceptor.Level.BODY
    }
    else{
        HttpLoggingInterceptor.Level.NONE
    }
}

fun <T> provideApiService(retrofit: Retrofit,cls: Class<T>) = retrofit.create(cls)

fun provideConvertAdapterFactory() = MoshiConverterFactory.create()

fun provideCallAdapterFactory(moshi: Moshi, networkStateManager: NetworkStateManager, ioDispatcher: CoroutineDispatcher) = ResultCallAdapterFactory(moshi,networkStateManager,ioDispatcher)

fun provideBaseUrl() = BuildConfig.BASE_URL.toHttpUrl()
