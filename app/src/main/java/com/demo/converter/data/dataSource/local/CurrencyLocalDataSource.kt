package com.demo.converter.data.dataSource.local

import com.demo.converter.common.AppConstant
import com.demo.converter.data.local.db.dao.CurrencyDao
import com.demo.converter.data.local.preference.CurrencyPreference
import com.demo.converter.data.mapper.CurrencyExchangeRateMapper
import com.demo.converter.data.mapper.CurrencyMapper
import com.demo.converter.domain.entity.Currency
import com.demo.converter.domain.entity.CurrencyExchangeRate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface CurrencyLocalDataSource {
    suspend fun getCurrencies():List<Currency>
    suspend fun getExchangeRates(currencyCode:String):List<CurrencyExchangeRate>
    suspend fun insertCurrencies(currencies:List<Currency>)
    suspend fun insertExchangeRates(exchangeRates:List<CurrencyExchangeRate>)
    suspend fun refreshExchangeRatesWithBase(currencyCode: String)
    suspend fun isExchangeRateSyncTimeElapsed(thresholdMillis:Long):Boolean
    suspend fun setExchangeRateSyncedTime(timeMillis:Long)
}

class CurrencyLocalDataSourceImpl(
    private val currencyDao: CurrencyDao,
    private val currencyPreference: CurrencyPreference,
    private val currencyMapper: CurrencyMapper,
    private val exchangeRateMapper: CurrencyExchangeRateMapper,
    private val ioDispatcher: CoroutineDispatcher,
    private val defaultDispatcher: CoroutineDispatcher
) : CurrencyLocalDataSource {

    override suspend fun getCurrencies() = withContext(defaultDispatcher){
        currencyMapper.mapTo(currencyDao.getCurrencies())
    }

    override suspend fun getExchangeRates(currencyCode: String) = withContext(defaultDispatcher){
        exchangeRateMapper.mapTo(currencyDao.getExchangeRates(currencyCode))
    }

    override suspend fun insertCurrencies(currencies: List<Currency>) = withContext(defaultDispatcher){
        currencyDao.insertCurrencies(currencyMapper.mapFrom(currencies))
    }

    override suspend fun insertExchangeRates(exchangeRates: List<CurrencyExchangeRate>) = withContext(defaultDispatcher){
        currencyDao.insertExchangeRates(exchangeRateMapper.mapFrom(exchangeRates))
    }

    override suspend fun refreshExchangeRatesWithBase(currencyCode: String) {
        if (currencyCode != AppConstant.BaseCurrency.CODE){
            val baseExchangeRates = getExchangeRates(AppConstant.BaseCurrency.CODE)
            withContext(defaultDispatcher){
                val fromExchangeRate = baseExchangeRates.find { it.code == currencyCode }?.rate
                if (fromExchangeRate != null){
                    val exchangeRates = baseExchangeRates.map { exchangeRate->
                        CurrencyExchangeRate(
                            currencyCode,
                            exchangeRate.code,
                            exchangeRate.rate/fromExchangeRate
                        )
                    }
                    insertExchangeRates(exchangeRates)
                }
            }
        }
    }

    override suspend fun isExchangeRateSyncTimeElapsed(thresholdMillis: Long) = withContext(ioDispatcher){
        (System.currentTimeMillis() - currencyPreference.getExchangeRateSyncedTime()) > thresholdMillis
    }

    override suspend fun setExchangeRateSyncedTime(timeMillis: Long) = withContext(ioDispatcher){
        currencyPreference.setExchangeRateSyncedTime(timeMillis)
    }
}