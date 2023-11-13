package com.kfh.converter.di

import com.kfh.converter.data.network.retrofit.HeaderInterceptor
import com.kfh.converter.data.network.NetworkStateManager
import com.kfh.converter.data.network.NetworkStateManagerImpl
import com.kfh.converter.data.network.retrofit.provideApiService
import com.kfh.converter.data.network.retrofit.provideBaseUrl
import com.kfh.converter.data.network.retrofit.provideCallAdapterFactory
import com.kfh.converter.data.network.retrofit.provideConvertAdapterFactory
import com.kfh.converter.data.network.retrofit.provideHttpLoggingInterceptor
import com.kfh.converter.data.network.retrofit.provideOkHttpClient
import com.kfh.converter.data.network.retrofit.provideRetrofit
import com.kfh.converter.data.repository.BankApi
import com.kfh.converter.data.repository.CurrencyApi
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
    factory<OkHttpClient>{ provideOkHttpClient(get(InterceptorQualifier.httpLogging),get(InterceptorQualifier.header)) }
    factory<Converter.Factory> { provideConvertAdapterFactory() }
    factory<CallAdapter.Factory> { provideCallAdapterFactory(get(),get(DispatcherQualifier.io)) }
    factory { provideBaseUrl() }
    single { provideRetrofit(get(),get(),get(),get()) }
}