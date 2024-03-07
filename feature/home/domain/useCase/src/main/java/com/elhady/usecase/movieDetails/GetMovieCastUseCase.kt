package com.elhady.usecase.movieDetails

import com.elhady.entities.ActorEntity
import com.elhady.usecase.repository.MovieRepository
import javax.inject.Inject

class GetMovieCastUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend fun getMovieCast(movieId: Int): List<ActorEntity> {
        return movieRepository.getMovieCast(movieId)?.cast
    }
}