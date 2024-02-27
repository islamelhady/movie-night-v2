package com.elhady.repository

import com.elhady.local.AppConfiguration
import com.elhady.remote.response.account.AccountDto
import com.elhady.remote.serviece.MovieService
import javax.inject.Inject

class AccountRepositoryImp @Inject constructor(
    private val movieService: MovieService,
    private val appConfiguration: AppConfiguration,
) : AccountRepository, BaseRepository() {
    override suspend fun loginWithUsernameAndPassword(userName: String, password: String): Boolean {
        val token = getRequestToken()
        val body = mapOf<String, Any>(
            "username" to userName,
            "password" to password,
            "request_token" to token
        ).toMap()
        return wrapApiCall { movieService.validateRequestTokenWithLogin(body) }
            .requestToken?.let { createSession(it); true } ?: false
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
        if (sessionResponse?.isSuccess == true) {
            saveSession(sessionResponse.sessionId.toString())
        }
    }

    private suspend fun saveSession(sessionId: String) {
        appConfiguration.saveSessionId(sessionId)
    }

}



