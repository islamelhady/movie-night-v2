package com.elhady.usecase.movieDetails

import com.elhady.movies.data.repository.MovieRepository
import javax.inject.Inject

class GetMovieRateUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(movieId: Int): Float {
        val response = movieRepository.getRatedMovie()
        return response?.let { list ->
            list.find { it.id == movieId }?.rating ?: 0F
        } ?: throw Throwable("Error")
    }

}