package com.example.saidur.database.converter

import androidx.room.TypeConverter
import com.example.saidur.data.model.Wind
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WindConverter {
    @TypeConverter
    fun fromWeatherContext(userContext: Wind?): String? {
        if (userContext == null) {
            return null
        }
        val gson = Gson()
        val type =
            object : TypeToken<Wind?>() {}.type
        return gson.toJson(userContext, type)
    }

    @TypeConverter
    fun toWeatherContext(userContextString: String?): Wind? {
        if (userContextString == null) {
            return null
        }
        val gson = Gson()
        val type =
            object : TypeToken<Wind?>() {}.type
        return gson.fromJson(userContextString, type)
    }
}