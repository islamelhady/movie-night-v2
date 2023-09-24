package com.elhady.movies.data.repository

interface AccountRepository {

    suspend fun loginWithUsernameAndPassword(userName: String, password: String):Boolean
}