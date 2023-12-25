package com.demo.converter.domain.usecase

import com.demo.converter.domain.repository.CurrencyRepository

class SyncExchangeRateUseCase(private val currencyRepository: CurrencyRepository) {
    suspend fun execute(currencyCode:String) = currencyRepository.syncExchangeRates(currencyCode)
}