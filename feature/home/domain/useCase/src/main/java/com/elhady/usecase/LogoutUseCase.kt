package com.elhady.usecase

import com.elhady.usecase.repository.AccountRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val accountRepository: AccountRepository) {
    suspend operator fun invoke() {
        accountRepository.logout()
    }
}