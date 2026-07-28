package com.elhady.movies.core.domain.usecase.home

import com.elhady.movies.core.domain.model.TVShowsEntity
import com.elhady.movies.core.domain.repository.TvShowRepository
import com.elhady.movies.core.domain.usecase.common.RefreshIfNeededUseCase
import javax.inject.Inject

class GetTvShowUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository,
    private val refreshIfNeededUseCase: RefreshIfNeededUseCase
) {
    suspend operator fun invoke(limit: Int = 10): List<TVShowsEntity> {
        refreshIfNeededUseCase()
        return tvShowRepository.getTvShowsFromDatabase()
            .also { if (it.isEmpty()) tvShowRepository.refreshTvShows() }
            .take(limit)
    }

}
