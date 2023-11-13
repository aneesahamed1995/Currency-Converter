package com.kfh.converter.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.qualifier.named
import org.koin.dsl.module

private val externalScope = CoroutineScope(SupervisorJob() +Dispatchers.Main)

object DispatcherQualifier{
    val io = named("ioDispatcher")
    val  default = named("defaultDispatcher")
}

object CoroutineScopeQualifier{
    val externalScope = named("externalCoroutineScope")
}

val coroutineModule = module {
    single(DispatcherQualifier.io) { Dispatchers.IO }
    single(DispatcherQualifier.default) { Dispatchers.Default }
    single(CoroutineScopeQualifier.externalScope) { externalScope }
}