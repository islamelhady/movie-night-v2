package com.elhady.movies.domain.usecases

import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.domain.mappers.ReviewMapper
import com.elhady.movies.domain.models.Review
import javax.inject.Inject

class GetReviewsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val reviewMapper: ReviewMapper
) {
    suspend operator fun invoke(movieId: Int): List<Review> {
        val response = movieRepository.getMovieReview(movieId = movieId)
        return response?.let { list ->
            list.map {
                reviewMapper.map(it)
            }
        } ?: throw Throwable("Not success")
    }
}