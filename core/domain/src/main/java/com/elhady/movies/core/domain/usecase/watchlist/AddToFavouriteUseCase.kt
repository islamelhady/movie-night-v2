package com.elhady.movies.core.domain.usecase.watchlist

import com.elhady.movies.core.domain.model.StatusEntity
import com.elhady.movies.core.domain.repository.AccountRepository
import javax.inject.Inject

class AddToFavouriteUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(
        movieId: Int,
        mediaType: String,
        isFavorite: Boolean = true
    ): StatusEntity {
        return accountRepository.addFavouriteList(movieId, mediaType, isFavorite)
    }
}
