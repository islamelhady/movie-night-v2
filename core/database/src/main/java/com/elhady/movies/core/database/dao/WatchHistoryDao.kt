package com.elhady.movies.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elhady.movies.core.database.entity.movie.MovieInWatchHistoryEntity

@Dao
interface WatchHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieToWatchHistory(movieInWatchHistoryEntity: MovieInWatchHistoryEntity)

    @Delete
    suspend fun deleteMovieFromWatchHistory(movieInWatchHistoryEntity: MovieInWatchHistoryEntity)

    @Query("select * from WATCH_HISTORY_MOVIES_TABLE")
    suspend fun getAllWatchHistory(): List<MovieInWatchHistoryEntity>

    @Query("select * from WATCH_HISTORY_MOVIES_TABLE where title like :keyword")
    suspend fun searchWatchHistory(keyword: String): List<MovieInWatchHistoryEntity>
}