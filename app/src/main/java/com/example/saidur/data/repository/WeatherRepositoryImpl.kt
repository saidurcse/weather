package com.example.saidur.data.repository

import androidx.lifecycle.LiveData
import com.example.saidur.data.api.WeatherApi
import com.example.saidur.data.model.LatLonResponseItem
import com.example.saidur.data.model.WeatherInfoResponse
import com.example.saidur.database.dao.WeatherLocalDataDAO
import com.example.saidur.utils.AppResult
import com.example.saidur.utils.Utils.handleApiError
import com.example.saidur.utils.Utils.handleSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepositoryImpl(private val api: WeatherApi, private val dao: WeatherLocalDataDAO) :
    WeatherRepository {
    override suspend fun getLatLongDataInfo(url: String, cityName: String): AppResult<ArrayList<LatLonResponseItem>> {
        return try {
            val response = api.getLatLonData(url, cityName)
            if (response.isSuccessful) {
                handleSuccess(response)
            } else {
                handleApiError(response)
            }
        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }

    override suspend fun getWeatherDataInfo(lat: String, lon: String): AppResult<WeatherInfoResponse> {
        return try {
            val response = api.getWeatherData(lat, lon)
            if (response.isSuccessful) {
                response.body()?.let {
                    withContext(Dispatchers.IO) {
                        // dao.AddAll(it) // We can insert data to the local db if we want
                    }
                }
                handleSuccess(response)
            } else {
                handleApiError(response)
            }
        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }

    override fun getAllOfflineDB(): LiveData<WeatherInfoResponse> {
        return dao.Get() // offline data
    }
}