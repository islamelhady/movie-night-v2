package com.elhady.movies.core.domain.usecase.details.moviedetails

import com.elhady.movies.core.domain.repository.AccountRepository
import javax.inject.Inject

class GetRatingMovieUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(movieId: Int): Float {
        val rating = accountRepository.getMovieRate().find {
            it.id == movieId
        }?.myRate ?: 0.0
        return rating.toFloat()
    }
}
