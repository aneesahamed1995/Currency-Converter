package com.demo.converter.common

import android.app.Application
import android.os.Build
import com.datatheorem.android.trustkit.TrustKit
import com.demo.converter.BuildConfig
import com.demo.converter.di.coroutineModule
import com.demo.converter.di.dataSourceModule
import com.demo.converter.di.modelMapperModule
import com.demo.converter.di.networkModule
import com.demo.converter.di.repoModule
import com.demo.converter.di.roomModule
import com.demo.converter.di.useCaseModule
import com.demo.converter.di.viewModelModule
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
                    networkModule, modelMapperModule, coroutineModule, roomModule
                )
            )
        }
        if (Build.VERSION.SDK_INT< Build.VERSION_CODES.N){
            TrustKit.initializeWithNetworkSecurityConfiguration(this)
        }
    }
}