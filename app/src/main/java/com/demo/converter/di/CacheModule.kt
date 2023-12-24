package com.demo.converter.di

import com.demo.converter.data.local.cache.CurrencyListCache
import org.koin.dsl.module

val cacheModule = module {
    single { CurrencyListCache() }
}