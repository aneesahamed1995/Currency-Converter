package com.kfh.converter.data.dataSource

import com.kfh.converter.data.mapper.CurrencyMapper
import com.kfh.converter.data.mapper.ExchangeRateMapper
import com.kfh.converter.data.repository.CurrencyApi
import com.kfh.converter.domain.entity.Currency
import com.kfh.converter.domain.entity.ExchangeRate
import com.kfh.converter.domain.entity.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface CurrencyRemoteDataSource {
    suspend fun getAllCurrency(): Result<List<Currency>>
    suspend fun getExchangeRates(baseCurrency:String): Result<List<ExchangeRate>>
}

class CurrencyRemoteDataSourceImpl(
    private val apiService: CurrencyApi,
    private val currencyMapper: CurrencyMapper,
    private val exchangeRateMapper: ExchangeRateMapper,
    private val ioDispatcher: CoroutineDispatcher
) : CurrencyRemoteDataSource {

    override suspend fun getAllCurrency() = withContext(ioDispatcher){
        when(val result = apiService.getAllCurrencies().execute()){
            is Result.Success-> Result.Success(currencyMapper.mapFrom(result.data.symbols))
            is Result.Failure-> result
        }
    }

    override suspend fun getExchangeRates(baseCurrency: String)= withContext(ioDispatcher){
        when(val result = apiService.getExchangeRate(baseCurrency).execute()){
            is Result.Success-> Result.Success(exchangeRateMapper.mapFrom(result.data.rates))
            is Result.Failure-> result
        }
    }

}