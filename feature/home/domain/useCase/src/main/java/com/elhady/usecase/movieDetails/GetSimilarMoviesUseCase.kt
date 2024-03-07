package com.elhady.usecase.movieDetails

import com.elhady.entities.MovieEntity

class GetSimilarMoviesUseCase {
    suspend fun getSimilarMovies(movieId: Int): List<MovieEntity> {
        val response = movieRepository.getSimilarMovies(movieId)
        return response?.let ( movieDtoMapper::map) ?: throw Throwable("Not success")
    }
}