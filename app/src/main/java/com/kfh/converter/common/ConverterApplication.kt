package com.kfh.converter.common

import android.app.Application
import android.os.Build
import com.datatheorem.android.trustkit.TrustKit
import com.kfh.converter.BuildConfig
import com.kfh.converter.di.coroutineModule
import com.kfh.converter.di.dataSourceModule
import com.kfh.converter.di.mapperModule
import com.kfh.converter.di.networkModule
import com.kfh.converter.di.repoModule
import com.kfh.converter.di.useCaseModule
import com.kfh.converter.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ConverterApplication:Application() {

    companion object {
        lateinit var instance: ConverterApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@ConverterApplication)
            modules(
                listOf(
                    repoModule, dataSourceModule, viewModelModule, useCaseModule,
                    networkModule, mapperModule, coroutineModule
                )
            )
        }
        if (Build.VERSION.SDK_INT< Build.VERSION_CODES.N){
            TrustKit.initializeWithNetworkSecurityConfiguration(this)
        }
    }
}