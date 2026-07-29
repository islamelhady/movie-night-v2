package com.elhady.movies.core.domain.repository

import com.elhady.movies.core.domain.model.auth.Profile

interface AuthRepository {

    suspend fun login(username: String, password: String): Boolean

    suspend fun logout()
    suspend fun getCurrentUsername(): String?

    suspend fun getAccountDetails(): Profile
    fun isUserLoggedIn():Boolean
}
