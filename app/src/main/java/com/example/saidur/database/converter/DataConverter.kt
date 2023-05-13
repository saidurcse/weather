package com.example.saidur.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.io.Serializable

class DataConverter : Serializable {

    @TypeConverter
    fun listToJson(value: List<Int>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Int>::class.java).toList()
}