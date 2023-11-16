package com.elhady.movies.domain.usecases

import com.elhady.movies.data.repository.AccountRepository
import javax.inject.Inject

class CheckIfLoggedInUseCase @Inject constructor(private val accountRepository: AccountRepository) {
    operator fun invoke(): Boolean {
        return !accountRepository.getSessionId().isNullOrBlank()
    }
}