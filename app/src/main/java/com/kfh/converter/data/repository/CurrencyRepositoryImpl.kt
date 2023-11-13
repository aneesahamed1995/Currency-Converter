package com.kfh.converter.data.repository

import com.kfh.converter.data.dataSource.CurrencyRemoteDataSource

import com.kfh.converter.domain.repository.CurrencyRepository

class CurrencyRepositoryImpl(private val currencyRemoteDataSource: CurrencyRemoteDataSource):CurrencyRepository {

    override suspend fun getAllCurrency() = currencyRemoteDataSource.getAllCurrency()

    override suspend fun getExchangeRates(baseCurrency: String) = currencyRemoteDataSource.getExchangeRates(baseCurrency)

}