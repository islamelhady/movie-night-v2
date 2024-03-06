package com.elhady.usecase

import com.elhady.entities.ProfileEntity
import com.elhady.usecase.repository.AuthRepository
import javax.inject.Inject

class GetAccountDetailsUseCase @Inject constructor(
    private val accountRepository: AuthRepository,
    private val accountMapper: AccountMapper
) {
    suspend operator fun invoke(): ProfileEntity {
        val account = accountRepository.getAccountDetails()
        return account?.let {
            accountMapper.map(it)
        } ?: throw Throwable("Account is null")
    }

}