package com.elhady.movies.core.domain.usecase.account

import com.elhady.movies.core.domain.model.account.UserListEntity
import com.elhady.movies.core.domain.repository.AccountRepository
import javax.inject.Inject

class GetUserListsUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(): List<UserListEntity> {
        return accountRepository.getUserLists()
    }
}
