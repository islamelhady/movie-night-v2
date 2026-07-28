package com.elhady.movies.core.domain.usecase.watchlist.mylist

import com.elhady.movies.core.domain.model.mylist.ListCreatedEntity
import com.elhady.movies.core.domain.repository.AccountRepository
import javax.inject.Inject

class GetListsCreatedUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(): List<ListCreatedEntity> {
        return accountRepository.getListCreated()
    }
}
