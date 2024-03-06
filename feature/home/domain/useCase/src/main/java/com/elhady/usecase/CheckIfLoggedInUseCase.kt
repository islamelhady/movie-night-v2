package com.elhady.usecase

import com.elhady.usecase.repository.AuthRepository
import javax.inject.Inject

class CheckIfLoggedInUseCase @Inject constructor(private val accountRepository: AuthRepository) {
    operator fun invoke(): Boolean {
        return !accountRepository.getSessionId().isNullOrBlank()
    }
}