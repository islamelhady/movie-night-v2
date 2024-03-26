package com.elhady.movies.core.domain.usecase.moviedetails

import com.elhady.movies.core.domain.repository.MovieRepository
import javax.inject.Inject

class GetRatingMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Float {
        val rating = movieRepository.getMovieRate().find {
            it.id == movieId
        }?.myRate ?: 0.0
        return rating.toFloat()
    }
}