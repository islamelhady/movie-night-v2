package com.elhady.movies.core.domain.usecase.tvshow

import com.elhady.movies.core.domain.model.tvshow.TvShows
import com.elhady.movies.core.domain.repository.TvShowRepository
import com.elhady.movies.core.domain.usecase.common.RefreshIfNeededUseCase
import javax.inject.Inject

class GetAiringTodayTvUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository,
    private val refreshIfNeededUseCase: RefreshIfNeededUseCase
) {
    suspend operator fun invoke(limit: Int = 6): List<TvShows> {
        refreshIfNeededUseCase()
        return tvShowRepository.getAiringTodayTvShowsFromDatabase()
            .also { if (it.isEmpty()) tvShowRepository.refreshAiringTodayTvShows() }
            .take(limit)
    }
}
