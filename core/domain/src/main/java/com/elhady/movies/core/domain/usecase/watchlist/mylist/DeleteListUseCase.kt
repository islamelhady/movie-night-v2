package com.elhady.movies.core.domain.usecase.watchlist.mylist

import com.elhady.movies.core.domain.model.StatusEntity
import com.elhady.movies.core.domain.repository.AccountRepository
import javax.inject.Inject

class DeleteListUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(listId: Int): StatusEntity {
        return accountRepository.deleteList(listId =listId)
    }
}
