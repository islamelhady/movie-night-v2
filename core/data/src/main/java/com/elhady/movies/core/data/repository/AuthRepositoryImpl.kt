package com.elhady.movies.core.data.repository

import com.elhady.movies.core.common.UnauthorizedThrowable
import com.elhady.movies.core.data.mapper.auth.ProfileDtoMapper
import com.elhady.movies.core.datastore.local.PreferenceStorage
import com.elhady.movies.core.domain.model.auth.Profile
import com.elhady.movies.core.domain.repository.AuthRepository
import com.elhady.movies.core.network.api.AccountApiService
import com.elhady.movies.core.network.api.AuthApiService
import com.elhady.movies.core.network.dto.auth.LoginRequest
import com.elhady.movies.core.network.exception.SafeApiCaller
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApiService: AuthApiService,
    private val accountApiService: AccountApiService,
    private val prefs: PreferenceStorage,
    private val domainProfileMapper: ProfileDtoMapper,
    private val safeApiCaller: SafeApiCaller
) : AuthRepository {

    override suspend fun login(username: String, password: String): Boolean {
        val token = getRequestToken()
        val body = LoginRequest(username, password, token)

        return safeApiCaller.execute { authApiService.login(body) }
            .requestToken?.let { createSession(it); saveUsername(username); true } ?: false
    }

    private suspend fun saveUsername(username: String) {
        prefs.setCurrentUserName(username)
    }

    private suspend fun createSession(requestToken: String) {
        safeApiCaller.execute { authApiService.createSession(requestToken) }
            .takeIf { it.isSuccess == true }
            ?.sessionId?.let { prefs.setSessionId(it) }
    }

    private suspend fun getRequestToken(): String {
        return safeApiCaller.execute { authApiService.createRequestToken() }
            .requestToken ?: throw UnauthorizedThrowable()
    }

    override suspend fun logout() {
        prefs.setSessionId("")
    }

    override suspend fun getCurrentUsername(): String? {
        return prefs.currentUserName
    }

    override suspend fun getAccountDetails(): Profile {
        val sessionId = prefs.sessionId
        val profileData =
            safeApiCaller.execute { accountApiService.getAccountDetails(sessionId ?: "") }
        return domainProfileMapper.map(profileData)
    }

    override fun isUserLoggedIn(): Boolean {
        val sessionId = prefs.sessionId
        return !sessionId.isNullOrBlank()
    }
}
