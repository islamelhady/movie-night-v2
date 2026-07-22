package com.elhady.movies.feature.auth.domain.usecase

import com.elhady.movies.feature.auth.domain.repository.AuthRepository
import javax.inject.Inject

class CheckIsUserLoggedInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Boolean {
        return authRepository.isUserLoggedIn()
    }
}
