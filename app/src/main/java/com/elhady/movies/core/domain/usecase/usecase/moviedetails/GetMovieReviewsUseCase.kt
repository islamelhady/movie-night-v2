package com.elhady.movies.core.domain.usecase.usecase.moviedetails

import com.elhady.movies.core.domain.entities.moviedetails.ReviewResponseEntity
import com.elhady.movies.core.domain.usecase.repository.MovieRepository
import javax.inject.Inject

class GetMovieReviewsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId:Int , page:Int): ReviewResponseEntity {
        return movieRepository.getMovieReviews(movieId , page)
    }
}