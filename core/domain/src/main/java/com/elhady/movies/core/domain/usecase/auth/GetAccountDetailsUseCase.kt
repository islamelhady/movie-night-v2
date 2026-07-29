package com.elhady.movies.core.domain.usecase.auth

import com.elhady.movies.core.domain.model.auth.Profile
import com.elhady.movies.core.domain.repository.AuthRepository
import javax.inject.Inject

class GetAccountDetailsUseCase @Inject constructor(
    private val accountRepository: AuthRepository,
) {
    suspend operator fun invoke(): Profile {
        return accountRepository.getAccountDetails()
    }
}
