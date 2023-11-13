package com.kfh.converter.di

import com.kfh.converter.data.mapper.CurrencyMapper
import com.kfh.converter.data.mapper.BankMapper
import com.kfh.converter.data.mapper.ExchangeRateMapper
import com.kfh.converter.view.mapper.BankDataUiStateMapper
import com.kfh.converter.view.mapper.CurrencyExchangeUiStateMapper
import org.koin.dsl.module

val mapperModule = module {
    factory { BankMapper() }
    factory { CurrencyMapper() }
    factory { BankDataUiStateMapper() }
    factory { CurrencyExchangeUiStateMapper() }
    factory { ExchangeRateMapper() }
}