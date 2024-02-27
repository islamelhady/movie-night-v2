package com.elhady.repository

import com.elhady.remote.response.account.AccountDto


interface AccountRepository {

    suspend fun loginWithUsernameAndPassword(userName: String, password: String):Boolean

    fun getSessionId(): String?

    suspend fun getAccountDetails(): AccountDto?

    suspend fun logout()

}