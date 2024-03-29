package com.elhady.movies.data.local


interface AppConfiguration {
    suspend fun getRequestDate(key: String): Long?
    suspend fun saveRequestDate(key: String,value: Long)
    suspend fun saveSessionId(value: String)
    fun getSessionId(): String?
}