package com.demo.converter.di

import com.demo.converter.data.dataSource.cache.CurrencyCacheDataSource
import com.demo.converter.data.dataSource.cache.CurrencyCacheDataSourceImpl
import com.demo.converter.data.dataSource.local.CurrencyLocalDataSource
import com.demo.converter.data.dataSource.local.CurrencyLocalDataSourceImpl
import com.demo.converter.data.dataSource.remote.CurrencyRemoteDataSource
import com.demo.converter.data.dataSource.remote.CurrencyRemoteDataSourceImpl
import org.koin.dsl.module

private val remoteDataSource = module {
    factory<CurrencyRemoteDataSource> { CurrencyRemoteDataSourceImpl(get(),get(),get(),get(DispatcherQualifier.io)) }
}

private val localDataSource = module {
    factory<CurrencyLocalDataSource> { CurrencyLocalDataSourceImpl(get(),get(),get(),get(),get(DispatcherQualifier.default),get(DispatcherQualifier.default)) }
}

private val cacheDataSource = module{
    factory<CurrencyCacheDataSource> { CurrencyCacheDataSourceImpl(get()) }
}

val dataSourceModule = module {
    includes(cacheDataSource)
    includes(remoteDataSource)
    includes(localDataSource)
}