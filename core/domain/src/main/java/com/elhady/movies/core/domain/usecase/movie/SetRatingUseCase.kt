package com.elhady.movies.core.domain.usecase.movie

import com.elhady.movies.core.domain.model.common.StatusEntity
import com.elhady.movies.core.domain.repository.AccountRepository
import javax.inject.Inject

class SetRatingUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(movieId:Int , rate:Float): StatusEntity {
        return accountRepository.setMovieRate(movieId , rate)
    }
}
