package com.elhady.movies.domain.usecases

import com.elhady.movies.data.repository.AccountRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val accountRepository: AccountRepository) {
    suspend operator fun invoke() {
        accountRepository.logout()
    }
}