package com.elhady.movies.core.database.dao.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elhady.movies.core.database.entity.movie.RecommendedMovieEntity

@Dao
interface RecommendedMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecommendedMovies(movies: List<RecommendedMovieEntity>)

    @Query("select * from RECOMMENDED_MOVIE_TABLE ORDER BY RANDOM()")
    suspend fun getRecommendedMovie(): List<RecommendedMovieEntity>

    @Query("delete from RECOMMENDED_MOVIE_TABLE")
    suspend fun clearAllRecommendedMovies()
}