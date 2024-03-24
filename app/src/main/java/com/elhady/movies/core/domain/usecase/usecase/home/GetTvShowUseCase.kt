package com.elhady.movies.core.domain.usecase.usecase.home

import com.elhady.movies.core.domain.entities.TVShowsEntity
import com.elhady.movies.core.domain.usecase.repository.TvShowRepository
import javax.inject.Inject

class GetTvShowUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository,
    private val refreshIfNeededUseCase: RefreshIfNeededUseCase
) {
    suspend operator fun invoke(limit: Int = 10): List<TVShowsEntity> {
//        refreshIfNeededUseCase()
        return tvShowRepository.getTvShowsFromDatabase()
            .also { if (it.isEmpty()) tvShowRepository.refreshTvShows() }
            .take(limit)
    }

}