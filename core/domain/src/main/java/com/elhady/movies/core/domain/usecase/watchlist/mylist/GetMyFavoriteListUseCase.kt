package com.elhady.movies.core.domain.usecase.watchlist.mylist

import com.elhady.movies.core.domain.model.MovieEntity
import com.elhady.movies.core.domain.repository.AccountRepository
import javax.inject.Inject

class GetMyFavoriteListUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(): List<MovieEntity> {
        return  accountRepository.getFavoriteMovies() + accountRepository.getFavoriteTv()
    }
}
