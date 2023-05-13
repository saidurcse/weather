package com.example.saidur.ui.home

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.saidur.data.model.LatLonResponseItem
import com.example.saidur.data.model.WeatherInfoResponse
import com.example.saidur.data.repository.WeatherRepository
import com.example.saidur.utils.AppResult
import com.example.saidur.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    val dataLoading = ObservableBoolean(false)

    val offlineWeather = MutableLiveData<WeatherInfoResponse>()

    val weatherData: LiveData<WeatherInfoResponse>
        get() = _weatherData
    private var _weatherData = MutableLiveData<WeatherInfoResponse>()

    val weatherLatLongData: LiveData<ArrayList<LatLonResponseItem>>
        get() = _weatherLatLongData
    private var _weatherLatLongData = MutableLiveData<ArrayList<LatLonResponseItem>>()

    val showError = SingleLiveEvent<String>()

    fun getAllOfflineDB(){
        offlineWeather.value = repository.getAllOfflineDB().value
    }

    fun fetchLatLonData(url: String, cityName: String) {
        dataLoading.set(true)
        viewModelScope.launch {
            val result = repository.getLatLongDataInfo(url, cityName)

            dataLoading.set(false)
            when (result) {
                is AppResult.Success -> {
                    _weatherLatLongData.value = result.successData
                    showError.value = null
                }
                is AppResult.Error -> showError.value = result.exception.message
            }
        }
    }

    fun fetchWeatherData(lat: String, lon: String) {
        dataLoading.set(true)
        viewModelScope.launch {
            val result = repository.getWeatherDataInfo(lat, lon)

            dataLoading.set(false)
            when (result) {
                is AppResult.Success -> {
                    _weatherData.value = result.successData
                    showError.value = null
                }
                is AppResult.Error -> showError.value = result.exception.message
            }
        }
    }

}
