package com.elhady.usecase

import com.elhady.usecase.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val accountRepository: AuthRepository) {
    suspend operator fun invoke() {
        accountRepository.logout()
    }
}