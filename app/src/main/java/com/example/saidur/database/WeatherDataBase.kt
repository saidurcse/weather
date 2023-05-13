package com.example.saidur.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.saidur.data.model.WeatherInfoResponse
import com.example.saidur.database.converter.*
import com.example.saidur.database.dao.WeatherLocalDataDAO

@Database(entities = [WeatherInfoResponse::class], version = 1, exportSchema = false)
@TypeConverters(DataConverter::class,
    WeatherItemConverters::class,
    CoordConverter::class,
    MainConverter::class,
    SysConverter::class,
    WindConverter::class,
    CloudsConverter::class)
abstract class WeatherDataBase : RoomDatabase() {
    abstract val weatherLocalDataDAO: WeatherLocalDataDAO
}