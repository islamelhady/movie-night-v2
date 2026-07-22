package com.elhady.movies.core.domain.usecase.watchhistory

import com.elhady.movies.core.common.domain.entities.MovieInWatchHistoryEntity
import com.elhady.movies.core.common.domain.repository.WatchHistoryRepository
import javax.inject.Inject

class InsertMovieToWatchHistoryUseCase @Inject constructor(
    private val repository: WatchHistoryRepository,
) {
    suspend operator fun invoke(movie: MovieInWatchHistoryEntity){
        repository.insertMovieToWatchHistory(movie)
    }
}
