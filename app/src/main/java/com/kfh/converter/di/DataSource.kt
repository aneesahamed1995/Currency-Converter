package com.kfh.converter.di

import com.kfh.converter.data.dataSource.BankRemoteDataSource
import com.kfh.converter.data.dataSource.BankRemoteDataSourceImpl
import com.kfh.converter.data.dataSource.CurrencyRemoteDataSource
import com.kfh.converter.data.dataSource.CurrencyRemoteDataSourceImpl
import org.koin.dsl.module

val dataSourceModule = module {
    factory<BankRemoteDataSource> { BankRemoteDataSourceImpl(get(),get(),get(DispatcherQualifier.io)) }
    factory<CurrencyRemoteDataSource> { CurrencyRemoteDataSourceImpl(get(),get(),get(),get(DispatcherQualifier.io)) }
}