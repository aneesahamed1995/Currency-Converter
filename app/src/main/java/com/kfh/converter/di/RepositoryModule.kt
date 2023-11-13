package com.kfh.converter.di

import com.kfh.converter.data.repository.BankRepositoryImpl
import com.kfh.converter.data.repository.CurrencyRepositoryImpl
import com.kfh.converter.domain.repository.BankRepository
import com.kfh.converter.domain.repository.CurrencyRepository
import org.koin.dsl.module


val repoModule = module {
    factory<BankRepository> { BankRepositoryImpl(get()) }
    factory<CurrencyRepository> { CurrencyRepositoryImpl(get()) }
}