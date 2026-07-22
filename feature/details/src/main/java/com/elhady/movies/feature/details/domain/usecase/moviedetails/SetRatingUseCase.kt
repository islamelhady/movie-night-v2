package com.elhady.movies.feature.details.domain.usecase.moviedetails

import com.elhady.movies.core.common.domain.entities.StatusEntity
import com.elhady.movies.core.common.domain.repository.MovieRepository
import javax.inject.Inject

class SetRatingUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId:Int , rate:Float): StatusEntity {
        return movieRepository.setMovieRate(movieId , rate)
    }
}
