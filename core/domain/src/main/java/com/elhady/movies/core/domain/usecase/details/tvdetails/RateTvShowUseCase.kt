package com.elhady.movies.core.domain.usecase.details.tvdetails

import com.elhady.movies.core.domain.model.StatusEntity
import com.elhady.movies.core.domain.repository.TvShowRepository
import javax.inject.Inject

class RateTvShowUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(rate: Double, tvShowId: Int): StatusEntity {
        return tvShowRepository.rateTvShow(rate, tvShowId)
    }
}
