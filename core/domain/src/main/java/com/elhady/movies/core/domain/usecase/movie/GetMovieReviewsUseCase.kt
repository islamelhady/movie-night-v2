package com.elhady.movies.core.domain.usecase.movie

import com.elhady.movies.core.domain.model.movie.ReviewResponse
import com.elhady.movies.core.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieReviewsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId:Int , page:Int): ReviewResponse {
        return movieRepository.getMovieReviews(movieId , page)
    }
}
