package com.demo.converter.di

import com.demo.converter.data.local.db.FakeCurrencyDaoImpl
import com.demo.converter.data.local.db.dao.CurrencyDao
import org.koin.dsl.module

val roomModuleTest = module {
    factory<CurrencyDao> { FakeCurrencyDaoImpl() }
}