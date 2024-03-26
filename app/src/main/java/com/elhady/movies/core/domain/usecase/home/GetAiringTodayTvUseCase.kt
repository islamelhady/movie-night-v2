package com.elhady.movies.core.domain.usecase.home

import com.elhady.movies.core.domain.entities.TVShowsEntity
import com.elhady.movies.core.domain.repository.MovieRepository
import javax.inject.Inject

class GetAiringTodayTvUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val refreshIfNeededUseCase: RefreshIfNeededUseCase
) {
    suspend operator fun invoke(limit: Int = 6): List<TVShowsEntity> {
        refreshIfNeededUseCase()
        return movieRepository.getAiringTodayTvShowsFromDatabase()
            .also { if (it.isEmpty()) movieRepository.refreshAiringTodayTvShows() }
            .take(limit)
    }
}