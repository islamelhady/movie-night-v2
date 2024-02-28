package com.elhady.usecase

import com.elhady.entities.AccountEntity
import com.elhady.usecase.repository.AccountRepository
import javax.inject.Inject

class GetAccountDetailsUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val accountMapper: AccountMapper
) {
    suspend operator fun invoke(): AccountEntity {
        val account = accountRepository.getAccountDetails()
        return account?.let {
            accountMapper.map(it)
        } ?: throw Throwable("Account is null")
    }

}