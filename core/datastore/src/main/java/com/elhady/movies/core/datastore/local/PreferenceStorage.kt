package com.elhady.movies.core.datastore.local

import com.elhady.movies.core.common.UserDataProvider

interface PreferenceStorage : UserDataProvider {
    override val sessionId: String?
    val currentUserName: String?
    val lastRefreshTime: Long?
    val lastWatchlistNotificationDate: String?

    val isDarkTheme: Boolean?

    suspend fun setSessionId(sessionId: String)

    suspend fun setCurrentUserName(currentUserName: String)

    suspend fun setLastRefreshTime(lastRefreshTime: Long)

    suspend fun setLastWatchlistNotificationDate(date: String)

    suspend fun setDarkTheme(isDark: Boolean)

    suspend fun clearPreferenceStorage()
}
