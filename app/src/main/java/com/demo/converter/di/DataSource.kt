package com.demo.converter.di

import com.demo.converter.data.dataSource.BankRemoteDataSource
import com.demo.converter.data.dataSource.BankRemoteDataSourceImpl
import com.demo.converter.data.dataSource.local.CurrencyLocalDataSource
import com.demo.converter.data.dataSource.local.CurrencyLocalDataSourceImpl
import com.demo.converter.data.dataSource.remote.CurrencyRemoteDataSource
import com.demo.converter.data.dataSource.remote.CurrencyRemoteDataSourceImpl
import org.koin.dsl.module

private val remoteDataSource = module {
    factory<BankRemoteDataSource> { BankRemoteDataSourceImpl(get(),get(),get(DispatcherQualifier.io)) }
    factory<CurrencyRemoteDataSource> { CurrencyRemoteDataSourceImpl(get(),get(),get(),get(DispatcherQualifier.io)) }
}

private val localDataSource = module {
    factory<CurrencyLocalDataSource> { CurrencyLocalDataSourceImpl(get(),get(),get(),get(DispatcherQualifier.io)) }
}

val dataSourceModule = module {
    includes(remoteDataSource)
    includes(localDataSource)
}