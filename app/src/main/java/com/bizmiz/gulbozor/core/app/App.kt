package com.bizmiz.gulbozor.core.app

import android.app.Application
import com.bizmiz.gulbozor.core.caches.AppCache
import com.bizmiz.gulbozor.core.caches.LoginHelper
import com.bizmiz.gulbozor.core.caches.PhoneNumberHelper
import com.bizmiz.gulbozor.core.caches.SetUpHelper
import com.bizmiz.gulbozor.core.di.dataModule
import com.bizmiz.gulbozor.core.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val modules = listOf(dataModule, viewModelModule)
        SetUpHelper.init(this)
        LoginHelper.init(this)
        AppCache.init(this)
        PhoneNumberHelper.init(this)
        startKoin { // use AndroidLogger as Koin Logger - default Level.INFO
            androidLogger()

            // use the Android context given there
            androidContext(this@App)

            // load properties from assets/koin.properties file
            androidFileProperties()

            // module list
            koin.loadModules(modules)
        }
    }
}