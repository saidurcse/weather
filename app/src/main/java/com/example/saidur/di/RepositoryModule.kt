package com.example.saidur.di

import com.example.saidur.data.api.WeatherApi
import com.example.saidur.data.repository.WeatherRepository
import com.example.saidur.data.repository.WeatherRepositoryImpl
import com.example.saidur.database.dao.WeatherLocalDataDAO
import org.koin.dsl.module

val repositoryModule = module {

    fun provideMovieRepository(api: WeatherApi, dao: WeatherLocalDataDAO): WeatherRepository {
        return WeatherRepositoryImpl(api, dao)
    }

    single { provideMovieRepository(get(), get()) }
}