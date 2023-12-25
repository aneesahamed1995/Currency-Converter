package com.demo.converter.di

import com.demo.converter.data.local.preference.CurrencyPreference
import com.demo.converter.data.local.preference.FakeCurrencyPreferenceImpl
import org.koin.dsl.module

val preferenceModuleTest = module {
    factory<CurrencyPreference> { FakeCurrencyPreferenceImpl() }
}