package com.elhady.movies.core.domain.usecase.account

import com.elhady.movies.core.domain.model.common.Status
import com.elhady.movies.core.domain.repository.AccountRepository
import javax.inject.Inject

class CreateUserListUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(listName: String): Status {
        return accountRepository.createUserList(listName)
    }
}
