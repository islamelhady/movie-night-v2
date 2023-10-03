package com.elhady.movies.data.local.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elhady.movies.data.local.database.entity.PopularMovieEntity
import com.elhady.movies.data.local.database.entity.TrendingMovieEntity
import com.elhady.movies.data.remote.response.MovieDto
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KFunction1

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularMovie(items: List<PopularMovieEntity>)

    @Query("DELETE FROM POPULAR_MOVIE_TABLE")
    suspend fun deletePopularMovie()

    @Query("SELECT * FROM POPULAR_MOVIE_TABLE")
    fun getPopularMovies(): Flow<List<PopularMovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrendingMovies(items: List<TrendingMovieEntity>)

    @Query("DELETE FROM TRENDING_MOVIE_TABLE")
    fun deleteTrendingMovies()

    @Query("SELECT * FROM TRENDING_MOVIE_TABLE")
    fun getAllTrendingMovies(): Flow<List<TrendingMovieEntity>>
}