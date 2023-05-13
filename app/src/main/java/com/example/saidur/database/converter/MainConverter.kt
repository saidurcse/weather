package com.example.saidur.database.converter

import androidx.room.TypeConverter
import com.example.saidur.data.model.Main
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainConverter {
    @TypeConverter
    fun fromWeatherContext(userContext: Main?): String? {
        if (userContext == null) {
            return null
        }
        val gson = Gson()
        val type =
            object : TypeToken<Main?>() {}.type
        return gson.toJson(userContext, type)
    }

    @TypeConverter
    fun toWeatherContext(userContextString: String?): Main? {
        if (userContextString == null) {
            return null
        }
        val gson = Gson()
        val type =
            object : TypeToken<Main?>() {}.type
        return gson.fromJson(userContextString, type)
    }
}