package com.demo.converter.view.mapper

import com.demo.converter.domain.entity.Currency
import com.demo.converter.view.model.CurrencyItemUiState

class CurrencyItemUiStateMapper {

    fun mapTo(currency: Currency) = CurrencyItemUiState(
        currency.name,
        currency.code,
        false
    )

    fun mapTo(currencies:List<Currency>) = currencies.map { mapTo(it) }
}