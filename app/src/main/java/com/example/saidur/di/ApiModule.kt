package com.example.saidur.di

import com.example.saidur.data.api.WeatherApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    fun provideWeatherApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    single { provideWeatherApi(get()) }
}