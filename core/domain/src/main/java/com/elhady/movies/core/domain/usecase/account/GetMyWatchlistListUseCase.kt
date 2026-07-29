package com.elhady.movies.core.domain.usecase.account

import com.elhady.movies.core.domain.model.movie.MovieEntity
import com.elhady.movies.core.domain.repository.AccountRepository
import javax.inject.Inject

class GetMyWatchlistListUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(): List<MovieEntity> {
        return  accountRepository.getWatchlistMovies() + accountRepository.getWatchlistTv()
    }
}
