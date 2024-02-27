package com.elhady.local

import javax.inject.Inject

class AppConfigurationImp @Inject constructor(private val dataStorePreferences: DataStorePreferences) :
    AppConfiguration {
    override suspend fun getRequestDate(key: String): Long? {
        return dataStorePreferences.readLong(key)
    }

    override suspend fun saveRequestDate(key: String, value: Long) {
        dataStorePreferences.writeLong(key, value)
    }

    override suspend fun saveSessionId(value: String) {
        dataStorePreferences.writeString(SESSION_ID_KEY, value)
    }

    override fun getSessionId(): String? {
        return dataStorePreferences.readString(SESSION_ID_KEY)
    }

    companion object DataStorePreferencesKeys {
        const val SESSION_ID_KEY = "session_id"
    }
}
