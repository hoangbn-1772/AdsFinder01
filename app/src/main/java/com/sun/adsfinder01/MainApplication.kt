package com.sun.adsfinder01

import android.app.Application
import com.facebook.stetho.Stetho
import com.sun.adsfinder01.di.networkModule
import com.sun.adsfinder01.di.repositoryModule
import com.sun.adsfinder01.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)

        startKoin {
            androidLogger()

            androidContext(this@MainApplication)

            modules(viewModelModule, networkModule, repositoryModule)
        }
    }
}
