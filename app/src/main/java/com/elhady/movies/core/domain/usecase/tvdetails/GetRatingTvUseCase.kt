package com.elhady.movies.core.domain.usecase.tvdetails

import com.elhady.movies.core.domain.repository.MovieRepository
import javax.inject.Inject

class GetRatingTvUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(tvShowId: Int): Float {
        val rating = movieRepository.getRateTvShow().find {
            it.id == tvShowId
        }?.rate ?: 0.0
        return rating.toFloat()
    }
}