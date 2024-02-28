package com.elhady.usecase.home

import com.elhady.entities.MediaEntity
import com.elhady.usecase.repository.MovieRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMysteryMoviesUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val mysteryMoviesMapper: MysteryMoviesMapper
) {
    suspend operator fun invoke(): List<MediaEntity> {
        return repository.getMysteryMovies().map(mysteryMoviesMapper::map)
    }
}