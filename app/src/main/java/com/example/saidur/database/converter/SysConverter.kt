package com.example.saidur.database.converter

import androidx.room.TypeConverter
import com.example.saidur.data.model.Sys
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SysConverter {
    @TypeConverter
    fun fromWeatherContext(userContext: Sys?): String? {
        if (userContext == null) {
            return null
        }
        val gson = Gson()
        val type =
            object : TypeToken<Sys?>() {}.type
        return gson.toJson(userContext, type)
    }

    @TypeConverter
    fun toWeatherContext(userContextString: String?): Sys? {
        if (userContextString == null) {
            return null
        }
        val gson = Gson()
        val type =
            object : TypeToken<Sys?>() {}.type
        return gson.fromJson(userContextString, type)
    }
}