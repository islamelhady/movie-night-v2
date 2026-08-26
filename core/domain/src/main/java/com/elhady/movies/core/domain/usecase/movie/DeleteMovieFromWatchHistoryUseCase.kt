package com.elhady.movies.core.domain.usecase.movie


import com.elhady.movies.core.domain.model.movie.MovieInWatchHistory
import com.elhady.movies.core.domain.repository.WatchHistoryRepository
import javax.inject.Inject

class DeleteMovieFromWatchHistoryUseCase @Inject constructor(
    private val repository: WatchHistoryRepository,
) {
    suspend operator fun invoke(movie: MovieInWatchHistory) {
        repository.deleteMovieFromWatchHistory(movie)
    }
}
