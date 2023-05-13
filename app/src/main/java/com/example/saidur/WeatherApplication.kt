package com.example.saidur

import androidx.multidex.MultiDexApplication
import com.example.saidur.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WeatherApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidLogger()
            androidContext(this@WeatherApplication)
            modules(
                apiModule,
                viewModelModule,
                repositoryModule,
                networkModule,
                databaseModule
            )
        }
    }

    companion object {
        var instance: WeatherApplication? = null
            public set
    }

    fun getInstance(): WeatherApplication? {
        return instance
    }
}
