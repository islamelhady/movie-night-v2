package com.elhady.movies.data.repository

import com.elhady.movies.data.remote.response.account.AccountDto

interface AccountRepository {

    suspend fun loginWithUsernameAndPassword(userName: String, password: String):Boolean

    fun getSessionId(): String?

    suspend fun getAccountDetails(): AccountDto?

}