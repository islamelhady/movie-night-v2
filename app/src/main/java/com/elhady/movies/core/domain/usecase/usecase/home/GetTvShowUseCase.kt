package com.elhady.movies.core.domain.usecase.usecase.home

import com.elhady.movies.core.domain.entities.TVShowsEntity
import com.elhady.movies.core.domain.usecase.repository.MovieRepository
import javax.inject.Inject

class GetTvShowUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val refreshIfNeededUseCase: RefreshIfNeededUseCase
) {
    suspend operator fun invoke(limit: Int = 10): List<TVShowsEntity> {
        refreshIfNeededUseCase()
        return movieRepository.getTvShowsFromDatabase()
            .also { if (it.isEmpty()) movieRepository.refreshTvShows() }
            .take(limit)
    }

}