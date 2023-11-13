package com.kfh.converter.di

import com.kfh.converter.view.base.ToolbarViewModel
import com.kfh.converter.view.converter.ConverterViewModel
import com.kfh.converter.view.exchange_rate.ExchangeRatesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ToolbarViewModel() }
    viewModel { ConverterViewModel(get(),get(),get()) }
    viewModel { parameters-> ExchangeRatesViewModel(selectedCountryCode = parameters.get(),get(),get(),get(DispatcherQualifier.default)) }
}