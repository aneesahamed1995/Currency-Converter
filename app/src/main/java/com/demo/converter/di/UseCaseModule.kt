package com.demo.converter.di

import com.demo.converter.domain.usecase.CurrencyConversionUseCase
import com.demo.converter.domain.usecase.GetCurrencyListUseCase
import com.demo.converter.domain.usecase.SyncExchangeRateUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { CurrencyConversionUseCase(get(),get(DispatcherQualifier.default)) }
    factory { GetCurrencyListUseCase(get()) }
    factory { SyncExchangeRateUseCase(get()) }
}