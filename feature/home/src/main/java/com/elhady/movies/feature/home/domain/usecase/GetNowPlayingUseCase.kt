package com.elhady.movies.feature.home.domain.usecase

import com.elhady.movies.core.common.domain.entities.MovieEntity
import com.elhady.movies.core.common.domain.repository.MovieRepository
import com.elhady.movies.core.common.domain.usecase.RefreshIfNeededUseCase
import javax.inject.Inject

class GetNowPlayingUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val refreshIfNeededUseCase: RefreshIfNeededUseCase
) {
    suspend operator fun invoke(limit: Int = 10): List<MovieEntity> {
        refreshIfNeededUseCase()
        return movieRepository.getNowPlayingMovies()
            .also { if (it.isEmpty()) movieRepository.refreshNowPlayingMovies() }
            .take(limit)
    }

}
