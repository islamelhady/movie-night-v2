package com.elhady.movies.core.domain.usecase.usecase.tvdetails

import com.elhady.movies.core.domain.entities.ReviewEntity
import com.elhady.movies.core.domain.usecase.repository.MovieRepository
import javax.inject.Inject

class GetTvDetailsReviewsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(tvShowId:Int): List<ReviewEntity> {
        val items = movieRepository.getTvShowReviews(tvShowId)
        return items
    }
}