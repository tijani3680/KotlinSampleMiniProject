package com.tijani.rememberkotlinskills

import android.app.Application
import com.tijani.rememberkotlinskills.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {


    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                retrofitModule,
                roomModule,
                repositoryModule,
                viewModelModule,
                appModule
            )
        }
    }
}