package com.elhady.movies.feature.watchlist.domain.usecase.watchhistory


import com.elhady.movies.core.common.domain.entities.MovieInWatchHistoryEntity
import com.elhady.movies.core.common.domain.repository.WatchHistoryRepository
import javax.inject.Inject

class DeleteMovieFromWatchHistoryUseCase @Inject constructor(
    private val repository: WatchHistoryRepository,
) {
    suspend operator fun invoke(movie: MovieInWatchHistoryEntity) {
        repository.deleteMovieFromWatchHistory(movie)
    }
}
