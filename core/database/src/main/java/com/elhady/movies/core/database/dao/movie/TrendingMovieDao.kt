package com.elhady.movies.core.database.dao.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elhady.movies.core.database.entity.movie.TrendingMoviesEntity

@Dao
interface TrendingMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrendingMovies(movies: List<TrendingMoviesEntity>)

    @Query("select * from TRENDING_MOVIES_TABLE ORDER BY RANDOM()")
    suspend fun getTrendingMovies(): List<TrendingMoviesEntity>

    @Query("delete from TRENDING_MOVIES_TABLE")
    suspend fun clearAllTrendingMovies()
}