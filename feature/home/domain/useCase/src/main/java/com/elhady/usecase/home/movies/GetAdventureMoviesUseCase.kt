package com.elhady.usecase.home.movies

import com.elhady.entities.MovieEntity
import com.elhady.usecase.repository.MovieRepository
import javax.inject.Inject

class GetAdventureMoviesUseCase @Inject constructor(
    private val repository: MovieRepository,
) {
    suspend operator fun invoke(limit: Int = 10): List<MovieEntity> {
        return repository.getAdventureMoviesFromDatabase()
            .also {
                if (it.isEmpty()) repository.refreshAdventureMovies()
            }.take(limit)
    }
}