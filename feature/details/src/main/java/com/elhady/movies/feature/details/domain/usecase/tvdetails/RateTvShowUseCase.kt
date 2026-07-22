package com.elhady.movies.feature.details.domain.usecase.tvdetails

import com.elhady.movies.core.common.domain.entities.StatusEntity
import com.elhady.movies.core.common.domain.repository.MovieRepository
import javax.inject.Inject

class RateTvShowUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(rate: Double, tvShowId: Int): StatusEntity {
        return movieRepository.rateTvShow(rate, tvShowId)
    }
}
