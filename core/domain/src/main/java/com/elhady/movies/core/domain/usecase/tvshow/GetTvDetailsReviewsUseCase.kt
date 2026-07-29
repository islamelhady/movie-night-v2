package com.elhady.movies.core.domain.usecase.tvshow

import com.elhady.movies.core.domain.model.common.ReviewEntity
import com.elhady.movies.core.domain.repository.TvShowRepository
import javax.inject.Inject

class GetTvDetailsReviewsUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository,
) {
    suspend operator fun invoke(tvShowId:Int): List<ReviewEntity> {
        val items = tvShowRepository.getTvShowReviews(tvShowId)
        return items
    }
}
