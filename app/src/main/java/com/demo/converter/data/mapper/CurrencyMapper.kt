package com.demo.converter.data.mapper

import com.demo.converter.data.local.db.entity.CurrencyLocalEntity
import com.demo.converter.domain.entity.Currency

class CurrencyMapper {

    fun mapTo(map: Map<String,String>?) = map?.map { (code,name)-> Currency(code, name) }?: emptyList()

    fun mapFrom(currencies:List<Currency>) = currencies.map { mapFrom(it) }

    fun mapTo(currencies:List<CurrencyLocalEntity>) = currencies.map { mapTo(it) }

    private fun mapFrom(currency: Currency) = CurrencyLocalEntity(currency.name, currency.code)

    private fun mapTo(localEntity: CurrencyLocalEntity) = Currency(localEntity.code,localEntity.name)
}