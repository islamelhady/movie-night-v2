package com.elhady.movies.domain.usecases.home

import com.elhady.movies.data.repository.MovieRepository
import com.elhady.movies.domain.mappers.AdventureMovieMapper
import com.elhady.movies.domain.models.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAdventureMoviesUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val adventureMovieMapper: AdventureMovieMapper
) {
    suspend operator fun invoke(): List<Media> {
        return repository.getAdventureMovies().map(adventureMovieMapper::map)
    }
}