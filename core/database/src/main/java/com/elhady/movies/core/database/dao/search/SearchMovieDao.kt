package com.elhady.movies.core.database.dao.search

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elhady.movies.core.database.entity.movie.MovieEntity

@Dao
interface SearchMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchMovies(movies: List<MovieEntity>)

    @Query("select * from MOVIE_TABLE ORDER BY RANDOM()")
    suspend fun getSearchMovie(): List<MovieEntity>
}