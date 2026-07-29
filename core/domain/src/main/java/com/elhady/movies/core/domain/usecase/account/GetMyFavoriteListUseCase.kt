package com.elhady.movies.core.domain.usecase.account

import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.core.domain.repository.AccountRepository
import javax.inject.Inject

class GetMyFavoriteListUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(): List<Movie> {
        return  accountRepository.getFavoriteMovies() + accountRepository.getFavoriteTv()
    }
}
