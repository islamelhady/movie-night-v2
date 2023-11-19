package com.elhady.movies.domain.usecases

import com.elhady.movies.data.repository.AccountRepository
import javax.inject.Inject

class GetSessionIdUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    operator fun invoke(): String? {
        return accountRepository.getSessionId()
    }
}