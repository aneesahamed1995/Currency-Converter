package com.demo.converter.data.repository

import com.demo.converter.data.dataSource.local.CurrencyLocalDataSource
import com.demo.converter.data.dataSource.remote.CurrencyRemoteDataSource
import com.demo.converter.domain.entity.CurrencyExchangeRate
import com.demo.converter.domain.entity.isSuccess
import com.demo.converter.domain.entity.value
import com.demo.converter.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit

class CurrencyRepositoryImpl(
    private val currencyRemoteDataSource: CurrencyRemoteDataSource,
    private val currencyLocalDataSource: CurrencyLocalDataSource
) : CurrencyRepository {

    private val syncThresholdMillis = TimeUnit.MINUTES.toMillis(30)

    override suspend fun getCurrencies() = currencyLocalDataSource.getCurrencies().ifEmpty {
        val result = currencyRemoteDataSource.getCurrencies()
        if (result.isSuccess) {
            currencyLocalDataSource.insertCurrencies(result.value)
        }
        currencyLocalDataSource.getCurrencies()
    }

    override suspend fun getExchangeRates(baseCurrency: String):Flow<List<CurrencyExchangeRate>> = flow {
        emit(currencyLocalDataSource.getExchangeRates(baseCurrency))
        val isSyncElapsed = currencyLocalDataSource.isSyncTimeElapsed(baseCurrency,syncThresholdMillis)
        if (isSyncElapsed){
            val result = currencyRemoteDataSource.getExchangeRates(baseCurrency)
            if (result.isSuccess){
                currencyLocalDataSource.insertExchangeRates(result.value)
                currencyLocalDataSource.setLastSyncTime(baseCurrency)
                emit(currencyLocalDataSource.getExchangeRates(baseCurrency))
            }
        }
    }

}