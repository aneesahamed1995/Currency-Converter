package com.kfh.converter.di

import com.kfh.converter.domain.usecase.GetCurrencyExchangesUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetCurrencyExchangesUseCase(get(),get(DispatcherQualifier.default)) }
}