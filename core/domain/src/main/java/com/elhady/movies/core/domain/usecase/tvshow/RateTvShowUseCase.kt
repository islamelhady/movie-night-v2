package com.elhady.movies.core.domain.usecase.tvshow

import com.elhady.movies.core.domain.model.common.StatusEntity
import com.elhady.movies.core.domain.repository.TvShowRepository
import javax.inject.Inject

class RateTvShowUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(rate: Double, tvShowId: Int): StatusEntity {
        return tvShowRepository.rateTvShow(rate, tvShowId)
    }
}
