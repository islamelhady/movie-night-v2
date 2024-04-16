package com.elhady.movies.core.data.repository.auth

import android.util.Log
import com.elhady.movies.core.data.local.PreferenceStorage
import com.elhady.movies.core.data.remote.request.LoginRequest
import com.elhady.movies.core.data.remote.service.MovieService
import com.elhady.movies.core.data.repository.BaseRepository
import com.elhady.movies.core.data.repository.mappers.domain.DomainProfileMapper
import com.elhady.movies.core.domain.entities.ProfileEntity
import com.elhady.movies.core.domain.usecase.repository.AuthRepository
import com.elhady.movies.core.domain.usecase.repository.UnauthorizedThrowable
import com.elhady.movies.presentation.viewmodel.login.log
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

