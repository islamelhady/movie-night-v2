package com.elhady.movies.core.domain.usecase.watchlist.mylist

import com.elhady.movies.core.domain.model.MovieEntity
import com.elhady.movies.core.domain.repository.AccountRepository
import javax.inject.Inject

class GetMyListDetailsByListIdUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(listId: Int = 0): List<MovieEntity> {
        return  accountRepository.getDetailsList(listId)
    }
}
