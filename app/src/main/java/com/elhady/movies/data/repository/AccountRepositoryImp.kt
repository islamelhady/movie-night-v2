package com.elhady.movies.data.repository

import com.elhady.movies.data.DataClassParser
import com.elhady.movies.data.remote.response.login.ErrorResponse
import com.elhady.movies.data.remote.service.MovieService

class AccountRepositoryImp(
    private val movieService: MovieService,
    val dataClassParser: DataClassParser
) :
    AccountRepository {
    override suspend fun loginWithUsernameAndPassword(userName: String, password: String): Boolean {
        return try {
            val token = getRequestToken()
            val body = mapOf<String, Any>(
                "username" to userName,
                "password" to password,
                "request_token" to token
            ).toMap()

            val validLoginRequest = movieService.validateRequestTokenWithLogin(body)
            if (validLoginRequest.isSuccessful) {
                validLoginRequest.body()?.requestToken?.let {
                    createSession(it)
                }
                true
            } else {
                val errorResponse = dataClassParser.parseFromJson(
                    validLoginRequest.errorBody().toString(),
                    ErrorResponse::class.java
                )
                throw Throwable(errorResponse.statusMessage)
            }
        } catch (e: Exception) {
            throw Throwable(e)
        }
    }

    private suspend fun getRequestToken(): String {
        val tokenResponse = movieService.getRequestToken()
        return tokenResponse.body()?.requestToken.toString()
    }

    private suspend fun createSession(requestToken: String) {
        movieService.createSession(requestToken).body()
    }

}



