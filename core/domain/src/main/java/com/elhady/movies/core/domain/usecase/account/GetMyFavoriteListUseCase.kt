package com.elhady.movies.core.domain.usecase.account

import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.core.domain.repository.AccountRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetMyFavoriteListUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(): List<Movie> = coroutineScope {
        val movies = async { accountRepository.getFavoriteMovies() }
        val tvShows = async { accountRepository.getFavoriteTv() }
        movies.await() + tvShows.await()
    }
}
