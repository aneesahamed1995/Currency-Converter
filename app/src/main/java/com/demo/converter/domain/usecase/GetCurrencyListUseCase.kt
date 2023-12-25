package com.demo.converter.domain.usecase

import com.demo.converter.domain.repository.CurrencyRepository

class GetCurrencyListUseCase(private val currencyRepository: CurrencyRepository) {

    suspend fun execute() = currencyRepository.getCurrencies()
}