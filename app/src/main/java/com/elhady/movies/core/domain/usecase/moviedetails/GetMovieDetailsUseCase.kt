package com.elhady.movies.core.domain.usecase.moviedetails

import com.elhady.movies.core.domain.entities.moviedetails.MovieDetailsEntity
import com.elhady.movies.core.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId:Int): MovieDetailsEntity {
        return movieRepository.getMoviesDetails(movieId)
    }
}