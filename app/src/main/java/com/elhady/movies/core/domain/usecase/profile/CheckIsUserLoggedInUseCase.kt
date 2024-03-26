package com.elhady.movies.core.domain.usecase.profile

import com.elhady.movies.core.domain.repository.AuthRepository
import javax.inject.Inject

class CheckIsUserLoggedInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Boolean {
        return authRepository.isUserLoggedIn()
    }
}