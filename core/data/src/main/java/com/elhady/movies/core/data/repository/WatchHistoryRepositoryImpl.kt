package com.elhady.movies.core.data.repository

import com.elhady.movies.core.data.base.BaseRepository
import com.elhady.movies.core.data.mapper.movie.DomainInWatchHistoryMoviesMapper
import com.elhady.movies.core.data.mapper.movie.LocalInWatchHistoryMoviesMapper
import com.elhady.movies.core.database.dao.WatchHistoryDao
import com.elhady.movies.core.domain.model.movie.MovieInWatchHistory
import com.elhady.movies.core.domain.repository.WatchHistoryRepository
import javax.inject.Inject


class WatchHistoryRepositoryImpl @Inject constructor(
    private val watchHistoryDao: WatchHistoryDao,
    private val domainInWatchHistoryMoviesMapper: DomainInWatchHistoryMoviesMapper,
    private val localInWatchHistoryMoviesMapper: LocalInWatchHistoryMoviesMapper
) : BaseRepository(), WatchHistoryRepository {

    override suspend fun insertMovieToWatchHistory(movieInWatchHistory: MovieInWatchHistory) {
        watchHistoryDao.insertMovieToWatchHistory(
            movieInWatchHistoryEntity = localInWatchHistoryMoviesMapper.map(
                movieInWatchHistory
            )
        )
    }

    override suspend fun deleteMovieFromWatchHistory(movieInWatchHistory: MovieInWatchHistory) {
        watchHistoryDao.deleteMovieFromWatchHistory(
            movieInWatchHistoryEntity = localInWatchHistoryMoviesMapper.map(
                movieInWatchHistory
            )
        )
    }

    override suspend fun getAllMoviesInWatchHistory(): List<MovieInWatchHistory> {
        return watchHistoryDao.getAllWatchHistory().map(domainInWatchHistoryMoviesMapper::map)
    }

    override suspend fun searchWatchHistoryWithKeyWord(keyword: String): List<MovieInWatchHistory> {
        return watchHistoryDao.searchWatchHistory("%${keyword}%").map {
            domainInWatchHistoryMoviesMapper.map(it)
        }
    }


}
