package com.kfh.converter.view.mapper

import com.kfh.converter.domain.entity.CurrencyExchange
import com.kfh.converter.view.model.CurrencyExchangeUiState

class CurrencyExchangeUiStateMapper {

    private fun mapFrom(currencyExchange: CurrencyExchange) = CurrencyExchangeUiState(
        currencyExchange.name,
        currencyExchange.code,
        currencyExchange.exchangeRate
    )

    fun mapFrom(currencyExchanges:List<CurrencyExchange>) = currencyExchanges.map { mapFrom(it) }
}