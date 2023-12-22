package com.demo.converter.view.mapper

import com.demo.converter.domain.entity.CurrencyExchange
import com.demo.converter.view.model.CurrencyExchangeUiState

class CurrencyExchangeUiStateMapper {

    private fun mapTo(currencyExchange: CurrencyExchange) = CurrencyExchangeUiState(
        currencyExchange.name,
        currencyExchange.code,
        currencyExchange.exchangeRate
    )

    fun mapTo(currencyExchanges:List<CurrencyExchange>) = currencyExchanges.map { mapTo(it) }
}