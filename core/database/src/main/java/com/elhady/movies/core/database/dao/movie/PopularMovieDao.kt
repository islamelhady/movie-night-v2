package com.elhady.movies.core.database.dao.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elhady.movies.core.database.entity.movie.PopularMovieEntity

@Dao
interface PopularMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularMovies(movies: List<PopularMovieEntity>)

    @Query("select * from POPULAR_MOVIE_TABLE ORDER BY RANDOM()")
    suspend fun getPopularMovies(): List<PopularMovieEntity>

    @Query("delete from POPULAR_MOVIE_TABLE")
    suspend fun clearAllPopularMovies()
}