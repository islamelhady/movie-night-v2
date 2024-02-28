package com.elhady.usecase

import com.elhady.usecase.repository.AccountRepository
import javax.inject.Inject

class GetSessionIdUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    operator fun invoke(): String? {
        return accountRepository.getSessionId()
    }
}