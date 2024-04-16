package com.elhady.movies.core.domain.usecase.usecase.moviedetails

import com.elhady.movies.core.domain.entities.StatusEntity
import com.elhady.movies.core.domain.usecase.repository.MovieRepository
import javax.inject.Inject

class SetRatingUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId:Int , rate:Float): StatusEntity {
        return movieRepository.setMovieRate(movieId , rate)
    }
}