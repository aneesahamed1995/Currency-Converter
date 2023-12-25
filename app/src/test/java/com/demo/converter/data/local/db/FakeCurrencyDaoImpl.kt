package com.demo.converter.data.local.db

import com.demo.converter.data.local.db.dao.CurrencyDao
import com.demo.converter.data.local.db.entity.CurrencyExchangeRateLocalEntity
import com.demo.converter.data.local.db.entity.CurrencyExchangeRateWithNameEntity
import com.demo.converter.data.local.db.entity.CurrencyLocalEntity

class FakeCurrencyDaoImpl:CurrencyDao {

    private val currencies = mutableListOf<CurrencyLocalEntity>()
    private val exchangeRates = mutableMapOf<String,List<CurrencyExchangeRateWithNameEntity>>()

    override fun getCurrencies() = currencies

    override suspend fun getExchangeRates(baseCurrencyCode: String) = exchangeRates.getOrDefault(
        baseCurrencyCode,
        emptyList()
    )

    override suspend fun insertCurrencies(currencies: List<CurrencyLocalEntity>) {
        this.currencies.addAll(currencies)
    }

    override suspend fun insertExchangeRates(exchangeRates: List<CurrencyExchangeRateLocalEntity>) {
        exchangeRates.getOrNull(0)?.let {
            this.exchangeRates.set(it.baseCurrencyCode,exchangeRates.map { CurrencyExchangeRateWithNameEntity(
                it.baseCurrencyCode,it.currencyCode,it.exchangeRate
            ) })
        }
    }

}