package com.elhady.movies.core.domain.usecase.watchlist

import com.elhady.movies.core.domain.model.StatusEntity
import com.elhady.movies.core.domain.repository.AccountRepository
import javax.inject.Inject

class AddToWatchList @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(movieId:Int, mediaType:String, isWatchlist: Boolean = true): StatusEntity {
        return accountRepository.addWatchlist(movieId,mediaType,isWatchlist)
    }
}
