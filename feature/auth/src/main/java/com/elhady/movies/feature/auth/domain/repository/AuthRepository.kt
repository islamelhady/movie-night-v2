package com.elhady.movies.feature.auth.domain.repository

import com.elhady.movies.core.common.ApiThrowable
import com.elhady.movies.core.common.domain.entities.ProfileEntity

interface AuthRepository {

    @Throws(exceptionClasses = [ApiThrowable::class])
    suspend fun login(username: String, password: String): Boolean

    suspend fun logout()
    suspend fun getCurrentUsername(): String?

    suspend fun getAccountDetails(): ProfileEntity
    fun isUserLoggedIn():Boolean
}