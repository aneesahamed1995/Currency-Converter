package com.demo.converter.di

import com.demo.converter.rule.MainCoroutineRule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import org.koin.dsl.module

@OptIn(ExperimentalCoroutinesApi::class)
val coroutineModuleTest = module {
    single { provideTestCoroutineScheduler() }
    factory(DispatcherQualifier.io) { provideTestStandardDispatcher(get()) }
    factory(DispatcherQualifier.default) { provideTestStandardDispatcher(get()) }
    factory<CoroutineScope>(CoroutineScopeQualifier.externalScope) { provideTestScope(get(DispatcherQualifier.default)) }
    factory { MainCoroutineRule(get(DispatcherQualifier.default)) }
}

@OptIn(ExperimentalCoroutinesApi::class)
private fun provideTestStandardDispatcher(scheduler: TestCoroutineScheduler): CoroutineDispatcher = StandardTestDispatcher(scheduler)

@OptIn(ExperimentalCoroutinesApi::class)
private fun provideTestCoroutineScheduler() = TestCoroutineScheduler()

@OptIn(ExperimentalCoroutinesApi::class)
private fun provideTestScope(testDispatcher: CoroutineDispatcher) = TestScope(testDispatcher)