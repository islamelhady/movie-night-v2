package com.elhady.movies.data


interface AppConfiguration {
    suspend fun getRequestDate(key: String): Long?
    suspend fun saveRequestDate(key: String,value: Long)
}