package com.elhady.movies.feature.home.domain.usecase

import com.elhady.movies.core.common.domain.entities.TVShowsEntity
import com.elhady.movies.core.common.domain.repository.MovieRepository
import com.elhady.movies.core.common.domain.usecase.RefreshIfNeededUseCase
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
