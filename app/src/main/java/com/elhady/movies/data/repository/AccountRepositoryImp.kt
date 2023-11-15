package com.elhady.movies.data.repository

import com.elhady.movies.data.DataClassParser
import com.elhady.movies.data.local.AppConfiguration
import com.elhady.movies.data.remote.response.account.AccountDto
import com.elhady.movies.data.remote.response.login.ErrorResponse
import com.elhady.movies.data.remote.service.MovieService
import javax.inject.Inject

class AccountRepositoryImp @Inject constructor(
    private val movieService: MovieService,
    private val dataClassParser: DataClassParser,
    private val appConfiguration: AppConfiguration,
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

    override fun getSessionId(): String? {
        return appConfiguration.getSessionId()
    }

    override suspend fun getAccountDetails(): AccountDto? {
        return movieService.getAccountDetails().body()
    }

    override suspend fun logout() {
        appConfiguration.saveSessionId("")
    }

    private suspend fun getRequestToken(): String {
        val tokenResponse = movieService.getRequestToken()
        return tokenResponse.body()?.requestToken.toString()
    }

    private suspend fun createSession(requestToken: String) {
        val sessionResponse = movieService.createSession(requestToken).body()
        if (sessionResponse?.success == true){
            saveSession(sessionResponse.sessionId.toString())
        }
    }

    private suspend fun saveSession(sessionId: String){
        appConfiguration.saveSessionId(sessionId)
    }

}



