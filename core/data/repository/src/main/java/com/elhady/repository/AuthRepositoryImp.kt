package com.elhady.repository

import com.elhady.entities.ProfileEntity
import com.elhady.local.AppConfiguration
import com.elhady.remote.request.LoginRequest
import com.elhady.remote.serviece.MovieService
import com.elhady.repository.mappers.domain.profile.DomainProfileMapper
import com.elhady.usecase.repository.AuthRepository
import com.elhady.usecase.repository.UnauthorizedThrowable
import javax.inject.Inject

class AuthRepositoryImp @Inject constructor(
    private val movieService: MovieService,
    private val appConfiguration: AppConfiguration,
    private val domainProfileMapper: DomainProfileMapper
) : BaseRepository(), AuthRepository {

    override suspend fun loginWithUsernameAndPassword(username: String, password: String): Boolean {
        val token = getRequestToken()
        val body = LoginRequest(username = username, password = password, requestToken = token)

        return wrapApiCall { movieService.login(body) }
            .requestToken?.let { createSession(it); saveUsername(it); true } ?: false
    }

    override suspend fun getCurrentUsername(): String? {
        TODO()
    }

    override suspend fun getAccountDetails(): ProfileEntity? {
        val sessionId = appConfiguration.getSessionId()
        val profileDate = movieService.getAccountDetails(sessionId!!).body()
        return domainProfileMapper.map(profileDate!!)
    }

    private suspend fun getRequestToken(): String {
        val tokenResponse = wrapApiCall { movieService.createRequestToken() }
        return tokenResponse.requestToken ?: throw UnauthorizedThrowable()
    }

    private suspend fun createSession(requestToken: String) {
        wrapApiCall { movieService.createSession(requestToken) }
            .takeIf { it.isSuccess == true }
            ?.sessionId?.let { saveUsername(it) }
    }

    private suspend fun saveUsername(sessionId: String) {
        appConfiguration.saveSessionId(sessionId)
    }

    override suspend fun isUserLoggedIn(): Boolean {
        val sessionId = appConfiguration.getSessionId()
        return sessionId != null
    }
    override suspend fun logout() {
        appConfiguration.saveSessionId("")
    }

}



