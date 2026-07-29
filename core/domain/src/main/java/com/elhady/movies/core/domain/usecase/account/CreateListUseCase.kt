package com.elhady.movies.core.domain.usecase.account

import com.elhady.movies.core.domain.repository.AccountRepository
import javax.inject.Inject

class CreateListUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(nameList:String): Boolean {
        return accountRepository.addList(name = nameList)
    }
}
