package com.elhady.movies.core.domain.usecase.account

import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.core.domain.repository.AccountRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetMyWatchlistListUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(): List<Movie> = coroutineScope {
        val movies = async { accountRepository.getWatchlistMovies() }
        val tvShows = async { accountRepository.getWatchlistTv() }
        movies.await() + tvShows.await()
    }
}
