package com.elhady.movies.domain.usecases

import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.domain.models.Trailer

class GetTrailerUseCase constructor(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(movieId: Int): Trailer {
        return movieRepository.getMovieTrailer(movieId)
    }
}