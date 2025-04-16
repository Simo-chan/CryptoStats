package com.example.cryptostats

import android.app.Application
import com.example.cryptostats.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CryptoStatsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CryptoStatsApp)
            androidLogger()
            modules(appModule)
        }
    }
}