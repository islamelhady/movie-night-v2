package com.elhady.movies.core.datastore.local

interface PreferenceStorage {
    val sessionId: String?
    val currentUserName: String?
    val lastRefreshTime: Long?
    suspend fun setSessionId(sessionId: String)

    suspend fun setCurrentUserName(currentUserName: String)

    suspend fun setLastRefreshTime(lastRefreshTime: Long)

    suspend fun clearPreferenceStorage()
}
