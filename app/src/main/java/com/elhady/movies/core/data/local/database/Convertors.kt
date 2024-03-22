package com.elhady.movies.core.data.local.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import java.util.Date
import javax.inject.Inject

@ProvidedTypeConverter
class Convertors @Inject constructor(val gson: Gson){

    @TypeConverter
    fun dateToLong(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun longToDate(long: Long): Date {
        return Date(long)
    }

    @TypeConverter
    fun fromList(value: List<String>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun fromString(value: String): List<String> {
        return gson.fromJson(value, Array<String>::class.java).toList()
    }

}