package com.elhady.movies.core.domain.usecase.usecase.profile

import com.elhady.movies.core.domain.usecase.repository.AuthRepository
import javax.inject.Inject

class CheckIsUserLoggedInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Boolean {
        val loginState = authRepository.isUserLoggedIn()
        if (loginState) {
            return true
        }
        return false
    }
}