package com.elhady.movies.core.domain.usecase.usecase.watchhistory


import com.elhady.movies.core.domain.entities.MovieInWatchHistoryEntity
import com.elhady.movies.core.domain.usecase.repository.WatchHistoryRepository
import javax.inject.Inject

class GetAllWatchHistoryMoviesUseCase @Inject constructor(
    private val repository: WatchHistoryRepository
) {
    suspend operator fun invoke(): List<MovieInWatchHistoryEntity> {
        return repository.getAllMoviesInWatchHistory()
    }

}