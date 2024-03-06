package com.elhady.usecase.repository

import com.elhady.entities.ProfileEntity

interface AuthRepository {

    suspend fun loginWithUsernameAndPassword(userName: String, password: String): Boolean

    suspend fun getCurrentUsername(): String?

    suspend fun getAccountDetails(): ProfileEntity?

    suspend fun logout()

}