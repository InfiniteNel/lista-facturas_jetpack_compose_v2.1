package com.jroslar.listafacturasjetpackcomposev21.ui

import android.app.Application
import com.jroslar.listafacturasjetpackcomposev21.di.dataModule
import com.jroslar.listafacturasjetpackcomposev21.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(dataModule, viewModelModule)
        }
    }
}