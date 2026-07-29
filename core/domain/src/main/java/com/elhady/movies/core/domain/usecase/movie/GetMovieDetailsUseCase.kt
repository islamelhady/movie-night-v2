package com.elhady.movies.core.domain.usecase.movie

import com.elhady.movies.core.domain.model.movie.MovieDetails
import com.elhady.movies.core.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId:Int): MovieDetails {
        return movieRepository.getMoviesDetails(movieId)
    }
}
