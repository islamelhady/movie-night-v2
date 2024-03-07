package com.elhady.usecase.movieDetails

import com.elhady.entities.MovieDetailsEntity
import com.elhady.usecase.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(movieId: Int): MovieDetailsEntity {
        return movieRepository.getDetailsMovies(movieId)
    }
}