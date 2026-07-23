package com.elhady.movies.feature.profile.domain.usecase

import com.elhady.movies.core.common.domain.entities.ProfileEntity
import com.elhady.movies.feature.auth.domain.repository.AuthRepository
import javax.inject.Inject

class GetAccountDetailsUseCase @Inject constructor(
    private val accountRepository: AuthRepository,
) {
    suspend operator fun invoke(): ProfileEntity {
        return accountRepository.getAccountDetails()
    }
}
