package com.elhady.movies.core.database.dao.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elhady.movies.core.database.entity.movie.TopRatedMovieEntity

@Dao
interface TopRatedMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopRatedMovies(movies: List<TopRatedMovieEntity>)

    @Query("select * from TOP_RATED_MOVIE_TABLE ORDER BY RANDOM()")
    suspend fun getTopRatedMovies(): List<TopRatedMovieEntity>

    @Query("delete from TOP_RATED_MOVIE_TABLE")
    suspend fun clearAllTopRatedMovies()
}