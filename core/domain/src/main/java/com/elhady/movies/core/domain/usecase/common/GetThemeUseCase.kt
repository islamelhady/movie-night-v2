package com.elhady.movies.core.domain.usecase.common

import com.elhady.movies.core.domain.repository.AuthRepository
import javax.inject.Inject

class GetThemeUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Boolean {
        return authRepository.getTheme()
    }
}
