package com.example.saidur.data.api

import com.example.saidur.BuildConfig
import com.example.saidur.data.model.LatLonResponseItem
import com.example.saidur.data.model.WeatherInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface WeatherApi {
    @GET("weather")  // get weather info
    suspend fun getWeatherData(@Query("lat") lat: String?,
                               @Query("lon") lon: String?,
                               @Query("APPID") app_id: String = BuildConfig.APP_ID): Response<WeatherInfoResponse>

    @GET // get lat lon data from city name
    suspend fun getLatLonData(@Url url: String,
                              @Query("q") city: String?,
                              @Query("APPID") app_id: String = BuildConfig.APP_ID): Response<ArrayList<LatLonResponseItem>>

}
