package com.elhady.usecase

import com.elhady.usecase.repository.AuthRepository
import javax.inject.Inject

class GetSessionIdUseCase @Inject constructor(
    private val accountRepository: AuthRepository,
) {
    operator fun invoke(): String? {
        return accountRepository.getSessionId()
    }
}