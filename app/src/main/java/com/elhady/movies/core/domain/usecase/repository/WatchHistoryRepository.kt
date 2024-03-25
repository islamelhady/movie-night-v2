package com.elhady.movies.core.domain.usecase.repository

import com.elhady.movies.core.domain.entities.MovieInWatchHistoryEntity


interface WatchHistoryRepository {
    suspend fun insertMovieToWatchHistory(movieInWatchHistoryEntity: MovieInWatchHistoryEntity)
    suspend fun deleteMovieFromWatchHistory(movieInWatchHistoryEntity: MovieInWatchHistoryEntity)
    suspend fun getAllMoviesInWatchHistory(): List<MovieInWatchHistoryEntity>
    suspend fun searchWatchHistoryWithKeyWord(keyword: String): List<MovieInWatchHistoryEntity>
}