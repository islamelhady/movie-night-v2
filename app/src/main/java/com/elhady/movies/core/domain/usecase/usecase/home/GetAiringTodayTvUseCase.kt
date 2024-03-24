package com.elhady.movies.core.domain.usecase.usecase.home

import android.util.Log
import com.elhady.movies.core.domain.entities.TVShowsEntity
import com.elhady.movies.core.domain.usecase.repository.TvShowRepository
import javax.inject.Inject

class GetAiringTodayTvUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository,
    private val refreshIfNeededUseCase: RefreshIfNeededUseCase
) {
    suspend operator fun invoke(limit: Int = 6): List<TVShowsEntity> {
//        refreshIfNeededUseCase()
        return tvShowRepository.getAiringTodayTvShowsFromDatabase()
            .also { if (it.isEmpty()) tvShowRepository.refreshAiringTodayTvShows() }
            .take(limit)
    }
}