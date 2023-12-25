package com.demo.converter.data.repository

import com.demo.converter.data.dataSource.cache.CurrencyCacheDataSource
import com.demo.converter.data.dataSource.local.CurrencyLocalDataSource
import com.demo.converter.data.dataSource.remote.CurrencyRemoteDataSource
import com.demo.converter.domain.entity.CurrencyExchangeRate
import com.demo.converter.domain.entity.Result
import com.demo.converter.domain.entity.isSuccess
import com.demo.converter.domain.entity.value
import com.demo.converter.domain.repository.CurrencyRepository
import java.util.concurrent.TimeUnit

class CurrencyRepositoryImpl(
    private val currencyCacheDataSource: CurrencyCacheDataSource,
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

    override suspend fun syncExchangeRates(currencyCode: String):Result<Unit> {
        val isSyncElapsed = currencyLocalDataSource.isExchangeRateSyncTimeElapsed(syncThresholdMillis)
        return if (isSyncElapsed){
            when(val result = currencyRemoteDataSource.getBaseExchangeRates()){
                is Result.Success->{
                    currencyLocalDataSource.insertExchangeRates(result.value)
                    currencyLocalDataSource.refreshExchangeRatesWithBase(currencyCode)
                    currencyCacheDataSource.setExchangeRates(currencyLocalDataSource.getExchangeRates(currencyCode))
                    currencyLocalDataSource.setExchangeRateSyncedTime(System.currentTimeMillis())
                    Result.Success(Unit)
                }
                is Result.Failure-> takeIf { getExchangeRates(currencyCode).isNotEmpty() }?.let { Result.Success(Unit) }?:result
            }
        }
        else {
            currencyLocalDataSource.refreshExchangeRatesWithBase(currencyCode)
            currencyCacheDataSource.setExchangeRates(currencyLocalDataSource.getExchangeRates(currencyCode))
            Result.Success(Unit)
        }
    }

    override suspend fun getExchangeRates(currencyCode: String):List<CurrencyExchangeRate>{
        return currencyCacheDataSource.getExchangeRates(currencyCode).ifEmpty {
            currencyLocalDataSource.getExchangeRates(currencyCode).also {
                currencyCacheDataSource.setExchangeRates(it)
            }
        }
    }

}