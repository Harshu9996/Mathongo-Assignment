package com.example.mathongoassignment

import android.app.Application
import com.example.mathongoassignment.di.appModule
import com.example.networking.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MathongoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(appModule, networkModule))
            androidContext(this@MathongoApplication)
        }
    }
}