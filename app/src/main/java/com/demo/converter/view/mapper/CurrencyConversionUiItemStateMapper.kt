package com.demo.converter.view.mapper

import com.demo.converter.domain.entity.CurrencyConversion
import com.demo.converter.view.model.CurrencyConversionItemUiState

class CurrencyConversionUiItemStateMapper {

    private fun mapTo(currencyConversion: CurrencyConversion) = CurrencyConversionItemUiState(
        currencyConversion.name,
        currencyConversion.code,
        currencyConversion.baseCode,
        currencyConversion.rate,
        currencyConversion.baseAmount,
        currencyConversion.amount
    )

    fun mapTo(currencyConversions:List<CurrencyConversion>) = currencyConversions.map { mapTo(it) }
}