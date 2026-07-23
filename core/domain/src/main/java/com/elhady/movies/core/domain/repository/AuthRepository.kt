package com.elhady.movies.core.domain.repository

import com.elhady.movies.core.domain.model.ProfileEntity

interface AuthRepository {

    suspend fun login(username: String, password: String): Boolean

    suspend fun logout()
    suspend fun getCurrentUsername(): String?

    suspend fun getAccountDetails(): ProfileEntity
    fun isUserLoggedIn():Boolean
}
