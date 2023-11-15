package com.elhady.movies.domain.usecases

import com.elhady.movies.data.repository.AccountRepository
import com.elhady.movies.domain.mappers.account.AccountMapper
import com.elhady.movies.domain.models.Account
import javax.inject.Inject

class GetAccountDetailsUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val accountMapper: AccountMapper
) {
    suspend operator fun invoke(): Account {
        val account = accountRepository.getAccountDetails()
        return account?.let {
            accountMapper.map(it)
        } ?: throw Throwable("Account is null")
    }

}