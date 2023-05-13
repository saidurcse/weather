package com.example.saidur.database.converter

import androidx.room.TypeConverter
import com.example.saidur.data.model.WeatherItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WeatherItemConverters {
    @TypeConverter
    fun fromProcessWeather(value: List<WeatherItem?>?): String? {
        if (value == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<WeatherItem?>?>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toProcessWeather(value: String?): List<WeatherItem>? {
        if (value == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<WeatherItem?>?>() {}.type
        return gson.fromJson(value, type)
    }
}