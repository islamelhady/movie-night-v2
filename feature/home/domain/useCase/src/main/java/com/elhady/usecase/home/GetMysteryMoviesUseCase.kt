package com.elhady.usecase.home

import com.elhady.entities.MovieEntity
import com.elhady.usecase.repository.MovieRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMysteryMoviesUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val mysteryMoviesMapper: MysteryMoviesMapper
) {
    suspend operator fun invoke(): List<MovieEntity> {
        return repository.getMysteryMovies().map(mysteryMoviesMapper::map)
    }
}