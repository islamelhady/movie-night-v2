package com.elhady.movies.domain.usecases

import com.elhady.movies.data.local.database.entity.WatchHistoryEntity
import com.elhady.movies.data.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWatchHistoryUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    operator fun invoke(): Flow<List<WatchHistoryEntity>> {
        return movieRepository.getAllMoviesWatch()
    }
}