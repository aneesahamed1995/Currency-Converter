package com.demo.converter.data.mapper

import com.demo.converter.data.local.db.entity.CurrencyExchangeRateLocalEntity
import com.demo.converter.data.local.db.entity.CurrencyExchangeRateWithNameEntity
import com.demo.converter.domain.entity.CurrencyExchangeRate

class CurrencyExchangeRateMapper {

    fun mapTo(baseCode:String, rates: Map<String,Double>?) = rates?.map { (code,rate)-> CurrencyExchangeRate(baseCode,code, rate) }?: emptyList()

    fun mapTo(exchangeRateLocalEntities:List<CurrencyExchangeRateWithNameEntity>) = exchangeRateLocalEntities.map { mapTo(it) }

    private fun mapTo(exchangeRateLocalEntity: CurrencyExchangeRateWithNameEntity) = CurrencyExchangeRate(
        exchangeRateLocalEntity.baseCurrencyCode,
        exchangeRateLocalEntity.currencyCode,
        exchangeRateLocalEntity.exchangeRate,
        exchangeRateLocalEntity.name
    )

    private fun mapFrom(exchangeRate:CurrencyExchangeRate) = CurrencyExchangeRateLocalEntity(
        exchangeRate.baseCode,
        exchangeRate.code,
        exchangeRate.rate
    )

    fun mapFrom(exchangeRates:List<CurrencyExchangeRate>) = exchangeRates.map { mapFrom(it) }

}