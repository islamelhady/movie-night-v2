package com.elhady.movies.core.database.dao.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elhady.movies.core.database.entity.movie.NowPlayingMovieEntity

@Dao
interface NowPlayingMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNowPlayingMovies(movies: List<NowPlayingMovieEntity>)

    @Query("select * from NOW_PLAYING_MOVIE_TABLE ORDER BY RANDOM()")
    suspend fun getNowPlayingMovies(): List<NowPlayingMovieEntity>

    @Query("delete from NOW_PLAYING_MOVIE_TABLE")
    suspend fun clearAllNowPlayingMovies()
}