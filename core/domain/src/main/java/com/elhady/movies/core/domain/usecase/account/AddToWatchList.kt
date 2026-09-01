package com.elhady.movies.core.domain.usecase.account

import com.elhady.movies.core.common.MediaType
import com.elhady.movies.core.domain.model.common.Status
import com.elhady.movies.core.domain.repository.AccountRepository
import javax.inject.Inject

class AddToWatchList @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(movieId:Int, mediaType:MediaType, isWatchlist: Boolean = true): Status {
        return accountRepository.addWatchlist(movieId,mediaType,isWatchlist)
    }
}
