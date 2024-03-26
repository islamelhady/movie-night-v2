package com.elhady.movies.core.domain.usecase.watchhistory


import com.elhady.movies.core.domain.entities.MovieInWatchHistoryEntity
import com.elhady.movies.core.domain.repository.WatchHistoryRepository
import javax.inject.Inject

class DeleteMovieFromWatchHistoryUseCase @Inject constructor(
    private val repository: WatchHistoryRepository,
) {
    suspend operator fun invoke(movie: MovieInWatchHistoryEntity) {
        repository.deleteMovieFromWatchHistory(movie)
    }
}