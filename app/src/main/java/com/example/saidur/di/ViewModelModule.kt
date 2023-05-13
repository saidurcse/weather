package com.example.saidur.di

import com.example.saidur.ui.home.WeatherViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        WeatherViewModel(repository = get())
    }

}