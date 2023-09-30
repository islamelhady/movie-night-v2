package com.elhady.movies.data

import javax.inject.Inject

interface AppConfiguration {

    suspend fun getRequestDate(key: String): Long?
    suspend fun saveRequestDate(key: String,value: Long)
}

class AppConfigurator @Inject constructor(private val dataStorePreferences: DataStorePreferences) :
    AppConfiguration {
    override suspend fun getRequestDate(key: String): Long? {
        return dataStorePreferences.readLong(key)
    }

    override suspend fun saveRequestDate(key: String, value: Long) {
        dataStorePreferences.writeLong(key, value)
    }

    companion object DataStorePreferencesKeys {
        const val SESSION_ID_KEY = "session_id"
    }

}