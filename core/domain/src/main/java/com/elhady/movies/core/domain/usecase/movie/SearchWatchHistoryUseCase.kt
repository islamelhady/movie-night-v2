package com.elhady.movies.core.domain.usecase.movie


import com.elhady.movies.core.domain.model.movie.MovieInWatchHistoryEntity
import com.elhady.movies.core.domain.repository.WatchHistoryRepository
import javax.inject.Inject

class SearchWatchHistoryUseCase @Inject constructor(
    private val repository: WatchHistoryRepository,
) {
    suspend operator fun invoke(keyword: String): List<MovieInWatchHistoryEntity> {
        return repository.searchWatchHistoryWithKeyWord(keyword)
    }
}
