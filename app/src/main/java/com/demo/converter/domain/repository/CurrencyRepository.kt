package com.demo.converter.domain.repository

import com.demo.converter.domain.entity.Currency
import com.demo.converter.domain.entity.CurrencyExchangeRate
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    suspend fun getCurrencies():List<Currency>
    suspend fun getExchangeRates(baseCurrency:String):Flow<List<CurrencyExchangeRate>>
}