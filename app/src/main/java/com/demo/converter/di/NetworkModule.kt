package com.demo.converter.di

import com.demo.converter.data.network.retrofit.HeaderInterceptor
import com.demo.converter.data.network.NetworkStateManager
import com.demo.converter.data.network.NetworkStateManagerImpl
import com.demo.converter.data.network.retrofit.provideApiService
import com.demo.converter.data.network.retrofit.provideBaseUrl
import com.demo.converter.data.network.retrofit.provideCallAdapterFactory
import com.demo.converter.data.network.retrofit.provideConvertAdapterFactory
import com.demo.converter.data.network.retrofit.provideHttpLoggingInterceptor
import com.demo.converter.data.network.retrofit.provideOkHttpClient
import com.demo.converter.data.network.retrofit.provideRetrofit
import com.demo.converter.data.repository.BankApi
import com.demo.converter.data.repository.CurrencyApi
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter

object InterceptorQualifier{
    val httpLogging = named("HttpLoggingInterceptor")
    val header = named("HeaderInterceptor")
}

private val interceptorModule = module {
    factory<Interceptor>(InterceptorQualifier.httpLogging) { provideHttpLoggingInterceptor() }
    factory<Interceptor>(InterceptorQualifier.header){ HeaderInterceptor() }
}

private val apiServiceModule = module {
    factory<BankApi> { provideApiService(get(),BankApi::class.java) }
    factory<CurrencyApi> { provideApiService(get(),CurrencyApi::class.java) }
}


val networkModule = module {
    includes(interceptorModule)
    includes(apiServiceModule)
    single<NetworkStateManager> { NetworkStateManagerImpl(androidContext()) }
    single { Moshi.Builder().build() }
    factory<OkHttpClient>{ provideOkHttpClient(get(InterceptorQualifier.httpLogging),get(InterceptorQualifier.header)) }
    factory<Converter.Factory> { provideConvertAdapterFactory() }
    factory<CallAdapter.Factory> { provideCallAdapterFactory(get(),get(),get(DispatcherQualifier.io)) }
    factory { provideBaseUrl() }
    single { provideRetrofit(get(),get(),get(),get()) }
}