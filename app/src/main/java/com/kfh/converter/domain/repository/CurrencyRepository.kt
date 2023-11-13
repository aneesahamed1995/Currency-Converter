package com.kfh.converter.domain.repository

import com.kfh.converter.domain.entity.Currency
import com.kfh.converter.domain.entity.ExchangeRate
import com.kfh.converter.domain.entity.Result

interface CurrencyRepository {
    suspend fun getAllCurrency():Result<List<Currency>>
    suspend fun getExchangeRates(baseCurrency:String):Result<List<ExchangeRate>>
}