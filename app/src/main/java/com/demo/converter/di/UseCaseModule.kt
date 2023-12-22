package com.demo.converter.di

import com.demo.converter.domain.usecase.GetCurrencyExchangesUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetCurrencyExchangesUseCase(get(),get(DispatcherQualifier.default)) }
}