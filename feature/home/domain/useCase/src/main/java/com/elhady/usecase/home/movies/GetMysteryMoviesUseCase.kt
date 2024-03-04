package com.elhady.usecase.home.movies

import com.elhady.entities.MovieEntity
import com.elhady.usecase.repository.MovieRepository
import javax.inject.Inject

class GetMysteryMoviesUseCase @Inject constructor(
    private val repository: MovieRepository,
) {
    suspend operator fun invoke(limit: Int = 10): List<MovieEntity> {
        return repository.getMysteryMoviesFromDatabase()
            .also {
                if (it.isEmpty()) repository.refreshMysteryMovies()
            }.take(limit)
    }
}