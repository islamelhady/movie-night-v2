package com.elhady.movies.core.domain.usecase.tvshow

import com.elhady.movies.core.domain.model.common.Status
import com.elhady.movies.core.domain.repository.TvShowRepository
import javax.inject.Inject

class RateTvShowUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(rate: Double, tvShowId: Int): Status {
        return tvShowRepository.rateTvShow(rate, tvShowId)
    }
}
