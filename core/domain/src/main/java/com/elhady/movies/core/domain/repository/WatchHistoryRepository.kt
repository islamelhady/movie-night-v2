package com.elhady.movies.core.domain.repository

import com.elhady.movies.core.domain.model.movie.MovieInWatchHistory


interface WatchHistoryRepository {
    suspend fun insertMovieToWatchHistory(movieInWatchHistory: MovieInWatchHistory)
    suspend fun deleteMovieFromWatchHistory(movieInWatchHistory: MovieInWatchHistory)
    suspend fun getAllMoviesInWatchHistory(): List<MovieInWatchHistory>
    suspend fun searchWatchHistoryWithKeyWord(keyword: String): List<MovieInWatchHistory>
}
