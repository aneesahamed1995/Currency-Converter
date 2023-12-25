package com.demo.converter.di

import com.demo.converter.view.converter.ConverterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModuleTest = module {
    viewModel { parameters-> ConverterViewModel(savedStateHandle = parameters.get(),get(),get(),get(),get(DispatcherQualifier.default)) }

}