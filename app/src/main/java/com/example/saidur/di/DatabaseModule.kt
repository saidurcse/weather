package com.example.saidur.di

import android.app.Application
import androidx.room.Room
import com.example.saidur.database.WeatherDataBase
import com.example.saidur.database.dao.WeatherLocalDataDAO
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(application: Application): WeatherDataBase {
        return Room.databaseBuilder(application, WeatherDataBase::class.java, "Weather")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideWeatherDao(database: WeatherDataBase): WeatherLocalDataDAO {
        return database.weatherLocalDataDAO
    }

    single { provideDatabase(androidApplication()) }
    single { provideWeatherDao(get()) }
}
