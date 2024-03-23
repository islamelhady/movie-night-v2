package com.elhady.movies.core.domain.usecase.usecase.tvdetails

import com.elhady.movies.core.domain.entities.StatusEntity
import com.elhady.movies.core.domain.usecase.repository.MovieRepository
import javax.inject.Inject

class RateTvShowUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(rate: Double, tvShowId: Int): StatusEntity {
        return movieRepository.rateTvShow(rate, tvShowId)
    }
}