package com.elhady.movies.core.domain.usecase.moviedetails

import com.elhady.movies.core.common.domain.entities.moviedetails.MovieDetailsEntity
import com.elhady.movies.core.common.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId:Int): MovieDetailsEntity {
        return movieRepository.getMoviesDetails(movieId)
    }
}
