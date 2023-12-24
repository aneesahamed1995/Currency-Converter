package com.demo.converter.domain.usecase

import com.demo.converter.domain.entity.CurrencyConversion
import com.demo.converter.domain.entity.CurrencyExchangeRate
import com.demo.converter.domain.repository.CurrencyRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CurrencyConversionUseCase(
    private val currencyRepository: CurrencyRepository,
    private val defaultDispatcher: CoroutineDispatcher
) {
    suspend fun execute(baseCurrency:String,baseAmount:Double) = getConvertedCurrencies(currencyRepository.getExchangeRates(baseCurrency),baseAmount)

    private suspend fun getConvertedCurrencies(
        currencyExchangeRates: List<CurrencyExchangeRate>,
        baseAmount:Double
    ) = withContext(defaultDispatcher){
        currencyExchangeRates.map { CurrencyConversion(
            it.baseCode,it.code,it.rate,it.name,baseAmount
        ) }
    }
}