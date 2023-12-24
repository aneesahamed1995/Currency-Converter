package com.demo.converter.di

import com.demo.converter.data.mapper.CurrencyMapper
import com.demo.converter.data.mapper.BankMapper
import com.demo.converter.data.mapper.CurrencyExchangeRateMapper
import com.demo.converter.view.mapper.BankDataUiStateMapper
import com.demo.converter.view.mapper.CurrencyConversionUiItemStateMapper
import com.demo.converter.view.mapper.CurrencyItemUiStateMapper
import org.koin.dsl.module

private val uiModelMapper = module {
    factory { CurrencyConversionUiItemStateMapper() }
    factory { CurrencyItemUiStateMapper() }
    factory { BankDataUiStateMapper() }
}

private val domainModelMapper = module {
    factory { BankMapper() }
    factory { CurrencyMapper() }
    factory { CurrencyExchangeRateMapper() }
}

val modelMapperModule = module {
    includes(uiModelMapper)
    includes(domainModelMapper)
}