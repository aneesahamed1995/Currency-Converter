package com.demo.converter.rule

import com.demo.converter.di.cacheModule
import com.demo.converter.di.coroutineModuleTest
import com.demo.converter.di.dataSourceModule
import com.demo.converter.di.modelMapperModule
import com.demo.converter.di.networkModule
import com.demo.converter.di.networkModuleTest
import com.demo.converter.di.preferenceModuleTest
import com.demo.converter.di.repoModule
import com.demo.converter.di.roomModuleTest
import com.demo.converter.di.useCaseModule
import com.demo.converter.di.viewModelModule
import com.demo.converter.di.viewModelModuleTest
import org.koin.test.KoinTestRule

val koinTestRule = KoinTestRule.create {
    allowOverride(true)
    modules(listOf(
        coroutineModuleTest,
        dataSourceModule,
        cacheModule,
        preferenceModuleTest,
        roomModuleTest,
        repoModule,
        networkModule,
        networkModuleTest,
        modelMapperModule,
        useCaseModule,
        viewModelModule,
        viewModelModuleTest
    )) }