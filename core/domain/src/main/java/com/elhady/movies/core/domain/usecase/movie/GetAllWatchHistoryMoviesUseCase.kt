package com.elhady.movies.core.domain.usecase.movie


import com.elhady.movies.core.domain.model.movie.MovieInWatchHistoryEntity
import com.elhady.movies.core.domain.repository.WatchHistoryRepository
import javax.inject.Inject

class GetAllWatchHistoryMoviesUseCase @Inject constructor(
    private val repository: WatchHistoryRepository
) {
    suspend operator fun invoke(): List<MovieInWatchHistoryEntity> {
        return repository.getAllMoviesInWatchHistory()
    }

}
