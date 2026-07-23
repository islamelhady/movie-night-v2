package com.elhady.movies.core.domain.usecase.details.moviedetails

import com.elhady.movies.core.domain.model.StatusEntity
import com.elhady.movies.core.domain.repository.MovieRepository
import javax.inject.Inject

class SetRatingUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId:Int , rate:Float): StatusEntity {
        return movieRepository.setMovieRate(movieId , rate)
    }
}
