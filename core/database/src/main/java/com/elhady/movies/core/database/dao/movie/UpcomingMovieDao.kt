package com.elhady.movies.core.database.dao.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elhady.movies.core.database.entity.movie.UpcomingMovieEntity

@Dao
interface UpcomingMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpcomingMovies(movies: List<UpcomingMovieEntity>)

    @Query("select * from UPCOMING_MOVIE_TABLE ORDER BY RANDOM()")
    suspend fun getUpcomingMovies(): List<UpcomingMovieEntity>

    @Query("delete from UPCOMING_MOVIE_TABLE")
    suspend fun clearAllUpcomingMovies()
}