package com.demo.converter.data.dataSource.cache

import com.demo.converter.data.local.cache.CurrencyListCache
import com.demo.converter.domain.entity.CurrencyExchangeRate
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

interface CurrencyCacheDataSource {
    suspend fun getExchangeRates(currencyCode:String):List<CurrencyExchangeRate>
    suspend fun setExchangeRates(exchangeRates:List<CurrencyExchangeRate>)
}

class CurrencyCacheDataSourceImpl(private val currencyListCache: CurrencyListCache):CurrencyCacheDataSource{

    private val cacheLock = Mutex()

    override suspend fun getExchangeRates(currencyCode:String) = cacheLock.withLock {
        if (currencyListCache.hasCache && currencyListCache.baseCurrencyCode == currencyCode){
            currencyListCache.exchangeRates!!
        }
        else emptyList<CurrencyExchangeRate>()
    }

    override suspend fun setExchangeRates(exchangeRates: List<CurrencyExchangeRate>) = cacheLock.withLock {
        currencyListCache.exchangeRates = exchangeRates
        currencyListCache.baseCurrencyCode = exchangeRates.getOrNull(0)?.baseCode
    }
}