package com.demo.converter.rule

import com.demo.converter.di.coroutineModuleTest
import com.demo.converter.di.modelMapperModule
import com.demo.converter.di.networkModule
import com.demo.converter.di.repoModule
import com.demo.converter.di.useCaseModule
import com.demo.converter.di.viewModelModule
import org.koin.test.KoinTestRule

val koinTestRule = KoinTestRule.create {
    allowOverride(true)
    modules(listOf(
        coroutineModuleTest,
        remoteDataSourceTestModule,
        localDataSourceTestModule,
        inMemoryDataSourceTestModule,
        diskDataSourceTestModule,
        repoModule,
        networkModule,
        networkModuleTest,
        modelMapperModule,
        useCaseModule,
        appModuleTest,
        viewModelModule
    )) }