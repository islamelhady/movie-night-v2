package com.elhady.movies.feature.auth.data

import android.util.Log
import com.elhady.movies.core.common.UnauthorizedThrowable
import com.elhady.movies.core.domain.model.ProfileEntity
import com.elhady.movies.core.datastore.local.PreferenceStorage
import com.elhady.movies.core.network.BaseRepository
import com.elhady.movies.core.network.model.request.LoginRequest
import com.elhady.movies.core.network.service.MovieService
import com.elhady.movies.core.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val movieService: MovieService,
    private val prefs: PreferenceStorage,
    private val domainProfileMapper: DomainProfileMapper
) : BaseRepository(), AuthRepository {

    override suspend fun login(username: String, password: String): Boolean {
        val token = getRequestToken()
        val body = LoginRequest(username, password, token)

        return wrapApiCall { movieService.login(body) }
            .requestToken?.let { createSession(it); saveUsername(username); true } ?: false
    }

    private suspend fun saveUsername(username: String) {
        prefs.setCurrentUserName(username)
    }

    private suspend fun createSession(requestToken: String) {
        wrapApiCall { movieService.createSession(requestToken) }
            .takeIf { it.isSuccess == true }
            ?.sessionId?.let { prefs.setSessionId(it) }
    }

    private suspend fun getRequestToken(): String {
        return wrapApiCall { movieService.createRequestToken() }
            .requestToken ?: throw UnauthorizedThrowable()
    }

    override suspend fun logout() {
        prefs.setSessionId("")
    }

    override suspend fun getCurrentUsername(): String? {
        return prefs.currentUserName
    }

    override suspend fun getAccountDetails(): ProfileEntity {
        val sessionId = prefs.sessionId
        val profileData = wrapApiCall { movieService.getAccountDetails() }
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

