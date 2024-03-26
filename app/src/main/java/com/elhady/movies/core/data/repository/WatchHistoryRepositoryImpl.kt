package com.elhady.movies.core.data.repository

import com.elhady.movies.core.data.local.database.MovieDao
import com.elhady.movies.core.data.repository.mappers.cash.movie.LocalInWatchHistoryMoviesMapper
import com.elhady.movies.core.data.repository.mappers.domain.movie.DomainInWatchHistoryMoviesMapper
import com.elhady.movies.core.domain.entities.MovieInWatchHistoryEntity
import com.elhady.movies.core.domain.repository.WatchHistoryRepository
import javax.inject.Inject


class WatchHistoryRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val domainInWatchHistoryMoviesMapper: DomainInWatchHistoryMoviesMapper,
    private val localInWatchHistoryMoviesMapper: LocalInWatchHistoryMoviesMapper
) : BaseRepository(), WatchHistoryRepository {

    override suspend fun insertMovieToWatchHistory(movieInWatchHistoryEntity: MovieInWatchHistoryEntity) {
        movieDao.insertMovieToWatchHistory(
            localInWatchHistoryMoviesMapper.map(
                movieInWatchHistoryEntity
            )
        )
    }

    override suspend fun deleteMovieFromWatchHistory(movieInWatchHistoryEntity: MovieInWatchHistoryEntity) {
        movieDao.deleteMovieFromWatchHistory(
            localInWatchHistoryMoviesMapper.map(
                movieInWatchHistoryEntity
            )
        )
    }

    override suspend fun getAllMoviesInWatchHistory(): List<MovieInWatchHistoryEntity> {
        return movieDao.getAllWatchHistoryVideos().map {
            domainInWatchHistoryMoviesMapper.map(it)
        }
    }

    override suspend fun searchWatchHistoryWithKeyWord(keyword: String): List<MovieInWatchHistoryEntity> {
        return movieDao.searchWatchHistory("%${keyword}%").map {
            domainInWatchHistoryMoviesMapper.map(it)
        }
    }


}