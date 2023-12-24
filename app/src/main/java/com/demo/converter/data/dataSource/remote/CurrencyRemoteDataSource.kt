package com.demo.converter.data.dataSource.remote

import com.demo.converter.common.AppConstant
import com.demo.converter.data.mapper.CurrencyMapper
import com.demo.converter.data.mapper.CurrencyExchangeRateMapper
import com.demo.converter.data.repository.CurrencyApi
import com.demo.converter.domain.entity.Currency
import com.demo.converter.domain.entity.CurrencyExchangeRate
import com.demo.converter.domain.entity.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface CurrencyRemoteDataSource {
    suspend fun getCurrencies(): Result<List<Currency>>
    suspend fun getBaseExchangeRates(): Result<List<CurrencyExchangeRate>>
}

class CurrencyRemoteDataSourceImpl(
    private val apiService: CurrencyApi,
    private val currencyMapper: CurrencyMapper,
    private val currencyExchangeRateMapper: CurrencyExchangeRateMapper,
    private val ioDispatcher: CoroutineDispatcher
) : CurrencyRemoteDataSource {

    override suspend fun getCurrencies() = withContext(ioDispatcher){
        when(val result = apiService.getAllCurrencies().execute()){
            is Result.Success-> Result.Success(currencyMapper.mapTo(result.data))
            is Result.Failure-> result
        }
    }

    override suspend fun getBaseExchangeRates()= withContext(ioDispatcher){
        when(val result = apiService.getExchangeRate().execute()){
            is Result.Success-> Result.Success(currencyExchangeRateMapper.mapTo(AppConstant.BaseCurrency.CODE,result.data.rates))
            is Result.Failure-> result
        }
    }

}