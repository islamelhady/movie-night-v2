package com.elhady.movies.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import javax.inject.Inject

@ProvidedTypeConverter
class Converters @Inject constructor(val gson: Gson){

    @TypeConverter
    fun listToJson(value: List<String>): String{
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<String>{
        return gson.fromJson(value,Array<String>::class.java).toList()
    }
}