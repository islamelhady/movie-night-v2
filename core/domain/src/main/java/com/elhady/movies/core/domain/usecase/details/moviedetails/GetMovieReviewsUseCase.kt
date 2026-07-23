package com.elhady.movies.core.domain.usecase.details.moviedetails

import com.elhady.movies.core.domain.model.moviedetails.ReviewResponseEntity
import com.elhady.movies.core.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieReviewsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId:Int , page:Int): ReviewResponseEntity {
        return movieRepository.getMovieReviews(movieId , page)
    }
}
