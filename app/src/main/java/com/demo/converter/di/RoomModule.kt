package com.demo.converter.di

import com.demo.converter.data.local.db.RoomDbHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val roomModule = module {
    single { RoomDbHelper(androidContext()) }
}

private fun provideCurrencyDao(roomDbHelper: RoomDbHelper) = roomDbHelper.db.currencyDao()