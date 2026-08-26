package com.elhady.movies.core.domain.usecase.account

import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.core.domain.repository.AccountRepository
import javax.inject.Inject

class GetMyListDetailsByListIdUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(listId: Int = 0): List<Movie> {
        return  accountRepository.getDetailsList(listId)
    }
}
