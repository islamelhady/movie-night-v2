package com.elhady.movies.domain.usecases.home

import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.domain.mappers.MysteryMoviesMapper
import com.elhady.movies.domain.models.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMysteryMoviesUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val mysteryMoviesMapper: MysteryMoviesMapper
) {
    suspend operator fun invoke(): List<Media> {
        return repository.getMysteryMovies().map(mysteryMoviesMapper::map)
    }
}