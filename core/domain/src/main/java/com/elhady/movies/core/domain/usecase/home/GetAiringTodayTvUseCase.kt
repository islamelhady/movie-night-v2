package com.elhady.movies.core.domain.usecase.home

import com.elhady.movies.core.domain.model.TVShowsEntity
import com.elhady.movies.core.domain.repository.TvShowRepository
import com.elhady.movies.core.domain.usecase.common.RefreshIfNeededUseCase
import javax.inject.Inject

class GetAiringTodayTvUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository,
    private val refreshIfNeededUseCase: RefreshIfNeededUseCase
) {
    suspend operator fun invoke(limit: Int = 6): List<TVShowsEntity> {
        refreshIfNeededUseCase()
        return tvShowRepository.getAiringTodayTvShowsFromDatabase()
            .also { if (it.isEmpty()) tvShowRepository.refreshAiringTodayTvShows() }
            .take(limit)
    }
}
