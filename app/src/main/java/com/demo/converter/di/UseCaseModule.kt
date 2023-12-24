package com.demo.converter.di

import com.demo.converter.domain.usecase.CurrencyConversionUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { CurrencyConversionUseCase(get(),get(DispatcherQualifier.default)) }
}