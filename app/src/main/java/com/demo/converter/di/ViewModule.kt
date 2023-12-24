package com.demo.converter.di

import com.demo.converter.view.base.ToolbarViewModel
import com.demo.converter.view.converter.ConverterViewModel
import com.demo.converter.view.currency_list.CurrencyListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ToolbarViewModel() }
    viewModel { ConverterViewModel(get(),get(),get(),get(),get(DispatcherQualifier.default)) }
    viewModel { parameters-> CurrencyListViewModel(selectedCurrencyCode = parameters.get(),get(),get(),get(DispatcherQualifier.default)) }
}