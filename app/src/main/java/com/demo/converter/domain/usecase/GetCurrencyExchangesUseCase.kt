package com.demo.converter.domain.usecase

import com.demo.converter.domain.entity.Currency
import com.demo.converter.domain.entity.CurrencyExchange
import com.demo.converter.domain.entity.CurrencyExchangeRate
import com.demo.converter.domain.entity.Result
import com.demo.converter.domain.entity.value
import com.demo.converter.domain.repository.CurrencyRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext

class GetCurrencyExchangesUseCase(
    private val currencyRepository: CurrencyRepository,
    private val defaultDispatcher: CoroutineDispatcher
) {
    suspend fun execute(baseCurrency:String) = supervisorScope {
        /*val deferredAllCurrencies = async { currencyRepository.getCurrencies() }
        val deferredExchangeRates = async { currencyRepository.getExchangeRates(baseCurrency) }
        val allCurrencyResult = deferredAllCurrencies.await()
        val exchangeRatesResult = deferredExchangeRates.await()
        when{
            allCurrencyResult is Result.Failure-> allCurrencyResult
            exchangeRatesResult is Result.Failure->exchangeRatesResult
            else-> withContext(defaultDispatcher){
                Result.Success(generateCurrencyExchangeList(exchangeRatesResult.value,allCurrencyResult.value))
            }
        }*/
    }

    /*fun generateCurrencyExchangeList(
        currencyExchangeRates: List<CurrencyExchangeRate>,
        currencies: List<Currency>
    ): List<CurrencyExchange> {
        return currencies.map { currency ->
            val exchangeRate = currencyExchangeRates.find { it.code == currency.code }?.rate ?: 0.0
            CurrencyExchange(currency.name, currency.code, exchangeRate)
        }
    }*/
}