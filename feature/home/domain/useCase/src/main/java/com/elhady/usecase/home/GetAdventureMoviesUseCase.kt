package com.elhady.usecase.home

import com.elhady.entities.MovieEntity
import com.elhady.usecase.repository.MovieRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAdventureMoviesUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val adventureMovieMapper: AdventureMovieMapper
) {
    suspend operator fun invoke(): List<MovieEntity> {
        return repository.getAdventureMovies().map(adventureMovieMapper::map)
    }
}