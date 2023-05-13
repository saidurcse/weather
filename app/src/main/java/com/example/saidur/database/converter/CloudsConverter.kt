package com.example.saidur.database.converter

import androidx.room.TypeConverter
import com.example.saidur.data.model.Clouds
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CloudsConverter {
    @TypeConverter
    fun fromWeatherContext(userContext: Clouds?): String? {
        if (userContext == null) {
            return null
        }
        val gson = Gson()
        val type =
            object : TypeToken<Clouds?>() {}.type
        return gson.toJson(userContext, type)
    }

    @TypeConverter
    fun toWeatherContext(userContextString: String?): Clouds? {
        if (userContextString == null) {
            return null
        }
        val gson = Gson()
        val type =
            object : TypeToken<Clouds?>() {}.type
        return gson.fromJson(userContextString, type)
    }
}