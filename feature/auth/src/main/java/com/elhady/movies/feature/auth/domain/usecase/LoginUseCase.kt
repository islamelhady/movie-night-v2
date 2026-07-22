package com.elhady.movies.feature.auth.domain.usecase

import com.elhady.movies.feature.auth.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val getIsValidLoginUseCase: GetIsValidLoginUseCase
) {
    suspend operator fun invoke(username: String, password: String): LoginError {
        val inputErrors = getIsValidLoginUseCase(username, password)
        return if (inputErrors != LoginError.NO_INPUT_ERRORS) {
            inputErrors
        } else {
            try {
                authRepository.login(username, password)
                LoginError.SUCCESS
            } catch (_: Throwable) {
                LoginError.REQUEST_ERROR
            }
        }
    }
}

enum class LoginError {
    USER_NAME_ERROR,
    PASSWORD_ERROR,
    REQUEST_ERROR,
    NO_INPUT_ERRORS,
    SUCCESS
}
