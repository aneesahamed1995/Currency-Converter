package com.demo.converter.di

import com.demo.converter.data.repository.BankRepositoryImpl
import com.demo.converter.data.repository.CurrencyRepositoryImpl
import com.demo.converter.domain.repository.BankRepository
import com.demo.converter.domain.repository.CurrencyRepository
import org.koin.dsl.module


val repoModule = module {
    factory<BankRepository> { BankRepositoryImpl(get()) }
    factory<CurrencyRepository> { CurrencyRepositoryImpl(get(),get()) }
}