package com.elhady.movies.core.data.repository

import android.util.Log
import com.elhady.movies.core.common.UnauthorizedThrowable
import com.elhady.movies.core.data.mapper.auth.DomainProfileMapper
import com.elhady.movies.core.datastore.local.PreferenceStorage
import com.elhady.movies.core.domain.model.auth.Profile
import com.elhady.movies.core.domain.repository.AuthRepository
import com.elhady.movies.core.data.base.BaseRepository
import com.elhady.movies.core.network.dto.auth.LoginRequest
import com.elhady.movies.core.network.api.AccountApiService
import com.elhady.movies.core.network.api.AuthApiService
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApiService: AuthApiService,
    private val accountApiService: AccountApiService,
    private val prefs: PreferenceStorage,
    private val domainProfileMapper: DomainProfileMapper
) : BaseRepository(), AuthRepository {

    override suspend fun login(username: String, password: String): Boolean {
        val token = getRequestToken()
        val body = LoginRequest(username, password, token)

        return wrapApiCall { authApiService.login(body) }
            .requestToken?.let { createSession(it); saveUsername(username); true } ?: false
    }

    private suspend fun saveUsername(username: String) {
        prefs.setCurrentUserName(username)
    }

    private suspend fun createSession(requestToken: String) {
        wrapApiCall { authApiService.createSession(requestToken) }
            .takeIf { it.isSuccess == true }
            ?.sessionId?.let { prefs.setSessionId(it) }
    }

    private suspend fun getRequestToken(): String {
        return wrapApiCall { authApiService.createRequestToken() }
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
        val profileData = wrapApiCall { accountApiService.getAccountDetails(sessionId ?: "") }
        return domainProfileMapper.map(profileData)
    }

    override fun isUserLoggedIn(): Boolean {
        val sessionId = prefs.sessionId
        return !sessionId.isNullOrBlank()
    }

    private fun Any.log() {
        Log.e("AuthRepositoryImp", "log(${this::class.java.simpleName}) : $this")
    }
}
