package com.amos_tech_code.travelapp

import android.app.Application
import com.amos_tech_code.travelapp.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TravelApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TravelApplication)
            modules(AppModule)
        }
    }
}