package com.example.saidur.database.converter

import androidx.room.TypeConverter
import com.example.saidur.data.model.Coord
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CoordConverter {
    @TypeConverter
    fun fromWeatherContext(userContext: Coord?): String? {
        if (userContext == null) {
            return null
        }
        val gson = Gson()
        val type =
            object : TypeToken<Coord?>() {}.type
        return gson.toJson(userContext, type)
    }

    @TypeConverter
    fun toWeatherContext(userContextString: String?): Coord? {
        if (userContextString == null) {
            return null
        }
        val gson = Gson()
        val type =
            object : TypeToken<Coord?>() {}.type
        return gson.fromJson(userContextString, type)
    }
}