package com.elhady.movies.core.domain.usecase.tvshow

import com.elhady.movies.core.domain.model.tvshow.TvShows
import com.elhady.movies.core.domain.repository.TvShowRepository
import com.elhady.movies.core.domain.usecase.common.RefreshIfNeededUseCase
import javax.inject.Inject

class GetTvShowUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository,
    private val refreshIfNeededUseCase: RefreshIfNeededUseCase
) {
    suspend operator fun invoke(limit: Int = 10): List<TvShows> {
        refreshIfNeededUseCase()
        return tvShowRepository.getTvShowsFromDatabase()
            .also { if (it.isEmpty()) tvShowRepository.refreshTvShows() }
            .take(limit)
    }
}
