package com.demo.converter.di

import android.content.Context
import com.demo.converter.common.AppConstant
import com.demo.converter.data.local.preference.CurrencyPreference
import com.demo.converter.data.local.preference.CurrencyPreferenceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val preferenceModule = module {
    factory<CurrencyPreference> { CurrencyPreferenceImpl(providePreference(androidContext(),AppConstant.PreferenceName.CURRENCY_PREFERENCES)) }
}

private fun providePreference(context: Context, preferenceName:String) = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)
