package com.elhady.movies.core.domain.usecase.account

import com.elhady.movies.core.domain.model.common.StatusEntity
import com.elhady.movies.core.domain.repository.AccountRepository
import javax.inject.Inject

class DeleteListUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(listId: Int): StatusEntity {
        return accountRepository.deleteList(listId =listId)
    }
}
