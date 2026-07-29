package com.elhady.movies.core.domain.usecase.tvshow

import com.elhady.movies.core.domain.repository.TvShowRepository
import javax.inject.Inject

class GetRatingTvUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int): Float {
        val rating = tvShowRepository.getRateTvShow().find {
            it.id == tvShowId
        }?.rate ?: 0.0
        return rating.toFloat()
    }
}
