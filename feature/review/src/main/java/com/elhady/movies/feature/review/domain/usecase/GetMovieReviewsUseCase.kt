package com.elhady.movies.feature.review.domain.usecase

import com.elhady.movies.core.common.domain.entities.moviedetails.ReviewResponseEntity
import com.elhady.movies.core.common.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieReviewsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int, page: Int): ReviewResponseEntity {
        return movieRepository.getMovieReviews(movieId, page)
    }
}
