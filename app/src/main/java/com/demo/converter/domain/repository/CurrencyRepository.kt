package com.demo.converter.domain.repository

import com.demo.converter.domain.entity.Currency
import com.demo.converter.domain.entity.CurrencyExchangeRate
import com.demo.converter.domain.entity.Result

interface CurrencyRepository {
    suspend fun getCurrencies():List<Currency>
    suspend fun getExchangeRates(baseCurrency:String):List<CurrencyExchangeRate>
    suspend fun syncExchangeRates(currencyCode: String):Result<Unit>
}