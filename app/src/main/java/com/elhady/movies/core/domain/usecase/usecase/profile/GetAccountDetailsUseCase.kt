package com.elhady.movies.core.domain.usecase.usecase.profile

import com.elhady.movies.core.domain.entities.ProfileEntity
import com.elhady.movies.core.domain.usecase.repository.AuthRepository
import javax.inject.Inject

class GetAccountDetailsUseCase @Inject constructor(
    private val accountRepository: AuthRepository,
) {
    suspend operator fun invoke(): ProfileEntity {
        return accountRepository.getAccountDetails()
    }
}