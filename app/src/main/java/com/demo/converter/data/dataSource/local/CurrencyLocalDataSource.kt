package com.demo.converter.data.dataSource.local

import com.demo.converter.data.local.db.dao.CurrencyDao
import com.demo.converter.data.mapper.CurrencyExchangeRateMapper
import com.demo.converter.data.mapper.CurrencyMapper
import com.demo.converter.domain.entity.Currency
import com.demo.converter.domain.entity.CurrencyExchangeRate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface CurrencyLocalDataSource {
    suspend fun getCurrencies():List<Currency>
    suspend fun getExchangeRates(baseCurrencyCode:String):List<CurrencyExchangeRate>
    suspend fun isSyncTimeElapsed(baseCurrencyCode:String, thresholdMillis:Long):Boolean
    suspend fun insertCurrencies(currencies:List<Currency>)
    suspend fun insertExchangeRates(exchangeRates:List<CurrencyExchangeRate>)
    suspend fun setLastSyncTime(currencyCode:String)
}

class CurrencyLocalDataSourceImpl(
    private val currencyDao: CurrencyDao,
    private val currencyMapper: CurrencyMapper,
    private val exchangeRateMapper: CurrencyExchangeRateMapper,
    private val defaultDispatcher: CoroutineDispatcher
) : CurrencyLocalDataSource {

    override suspend fun getCurrencies() = withContext(defaultDispatcher){
        currencyMapper.mapTo(currencyDao.getCurrencies())
    }

    override suspend fun getExchangeRates(baseCurrencyCode: String) = withContext(defaultDispatcher){
        exchangeRateMapper.mapTo(currencyDao.getExchangeRates(baseCurrencyCode))
    }

    override suspend fun isSyncTimeElapsed(baseCurrencyCode: String,thresholdMillis:Long ) = currencyDao.isCurrencySyncTimeElapsed(baseCurrencyCode,thresholdMillis)

    override suspend fun insertCurrencies(currencies: List<Currency>) = withContext(defaultDispatcher){
        currencyDao.insertCurrencies(currencyMapper.mapFrom(currencies))
    }

    override suspend fun insertExchangeRates(exchangeRates: List<CurrencyExchangeRate>) = withContext(defaultDispatcher){
        currencyDao.insertExchangeRates(exchangeRateMapper.mapFrom(exchangeRates))
    }

    override suspend fun setLastSyncTime(currencyCode: String){
        currencyDao.setLastSyncTime(currencyCode)
    }
}