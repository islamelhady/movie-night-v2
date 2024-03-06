package com.elhady.usecase

import com.elhady.usecase.repository.AuthRepository
import javax.inject.Inject

class LoginWithUsernameAndPasswordUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(userName: String, password: String): LoginError {
        return try {
            authRepository.loginWithUsernameAndPassword(userName, password)
            LoginError.SUCCESS
        } catch (throwable: Throwable) {
            LoginError.REQUEST_ERROR
        }
    }
}

enum class LoginError {
    SUCCESS,
    REQUEST_ERROR
}
