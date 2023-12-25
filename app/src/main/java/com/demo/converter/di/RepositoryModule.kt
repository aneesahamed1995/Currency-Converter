package com.demo.converter.di

import com.demo.converter.data.repository.CurrencyRepositoryImpl
import com.demo.converter.domain.repository.CurrencyRepository
import org.koin.dsl.module


val repoModule = module {
    factory<CurrencyRepository> { CurrencyRepositoryImpl(get(),get(),get()) }
}