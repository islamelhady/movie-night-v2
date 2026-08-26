package com.elhady.movies.core.domain.usecase.auth

import com.elhady.movies.core.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val getIsValidLoginUseCase: GetIsValidLoginUseCase
) {
    suspend operator fun invoke(username: String, password: String): LoginError {
        val inputErrors = getIsValidLoginUseCase(username, password)
        if (inputErrors != LoginError.NO_INPUT_ERRORS) {
            return inputErrors
        }
        authRepository.login(username, password)
        return LoginError.SUCCESS
    }
}

enum class LoginError {
    USER_NAME_ERROR,
    PASSWORD_ERROR,
    NO_INPUT_ERRORS,
    SUCCESS
}
