package com.elhady.movies.core.domain.repository

import com.elhady.movies.core.data.ApiThrowable
import com.elhady.movies.core.domain.entities.ProfileEntity


interface AuthRepository {

    @Throws(exceptionClasses = [ApiThrowable::class])
    suspend fun login(username: String, password: String): Boolean

    suspend fun logout()
    suspend fun getCurrentUsername(): String?

    suspend fun getAccountDetails(): ProfileEntity
    fun isUserLoggedIn():Boolean
}