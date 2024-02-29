package com.elhady.usecase

import javax.inject.Inject

class LoginWithUsernameAndPasswordUseCase @Inject constructor(private val accountRepository: AccountRepository) {

    suspend operator fun invoke(userName: String, password: String): LoginError {
        return try {
            accountRepository.loginWithUsernameAndPassword(userName, password)
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