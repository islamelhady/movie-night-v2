package com.elhady.movies.core.domain.usecase.common

import com.elhady.movies.core.domain.repository.AuthRepository
import javax.inject.Inject

class SaveThemeUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(isDark: Boolean) {
        authRepository.saveTheme(isDark)
    }
}
