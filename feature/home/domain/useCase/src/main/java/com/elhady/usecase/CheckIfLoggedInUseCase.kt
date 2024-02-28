package com.elhady.usecase

import com.elhady.usecase.repository.AccountRepository
import javax.inject.Inject

class CheckIfLoggedInUseCase @Inject constructor(private val accountRepository: AccountRepository) {
    operator fun invoke(): Boolean {
        return !accountRepository.getSessionId().isNullOrBlank()
    }
}