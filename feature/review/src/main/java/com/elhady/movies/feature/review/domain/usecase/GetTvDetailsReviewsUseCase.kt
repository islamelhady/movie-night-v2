package com.elhady.movies.feature.review.domain.usecase

import com.elhady.movies.core.common.domain.entities.ReviewEntity
import com.elhady.movies.core.common.domain.repository.MovieRepository
import javax.inject.Inject

class GetTvDetailsReviewsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(tvShowId: Int): List<ReviewEntity> {
        return movieRepository.getTvShowReviews(tvShowId)
    }
}
