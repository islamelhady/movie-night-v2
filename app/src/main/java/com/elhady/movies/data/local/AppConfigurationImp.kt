package com.elhady.movies.data.local

import javax.inject.Inject

class AppConfigurationImp @Inject constructor(private val dataStorePreferences: DataStorePreferences) :
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
