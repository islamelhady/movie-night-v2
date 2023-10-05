package com.elhady.movies.data.local.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elhady.movies.data.local.database.entity.NowPlayingMovieEntity
import com.elhady.movies.data.local.database.entity.PopularMovieEntity
import com.elhady.movies.data.local.database.entity.TrendingMovieEntity
import com.elhady.movies.data.local.database.entity.UpcomingMovieEntity
import com.elhady.movies.data.remote.response.MovieDto
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularMovie(items: List<PopularMovieEntity>)

    @Query("DELETE FROM POPULAR_MOVIE_TABLE")
    suspend fun deletePopularMovie()

    @Query("SELECT * FROM POPULAR_MOVIE_TABLE")
    fun getPopularMovies(): Flow<List<PopularMovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrendingMovies(items: List<TrendingMovieEntity>)

    @Query("DELETE FROM TRENDING_MOVIE_TABLE")
    suspend fun deleteTrendingMovies()

    @Query("SELECT * FROM TRENDING_MOVIE_TABLE")
    fun getAllTrendingMovies(): Flow<List<TrendingMovieEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpcomingMovie(items: List<UpcomingMovieEntity>)

    @Query("DELETE FROM UPCOMING_MOVIE_TABLE")
    suspend fun deleteUpcomingMovies()

    @Query("SELECT * FROM UPCOMING_MOVIE_TABLE")
    fun getUpcomingMovies(): Flow<List<UpcomingMovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNowPlayingMovies(items: List<NowPlayingMovieEntity>)

    @Query("DELETE FROM NOW_PLAYING_MOVIE_TABLE")
    suspend fun deleteNowPlayingMovies()

    @Query("SELECT * FROM NOW_PLAYING_MOVIE_TABLE")
    fun getNowPlayingMovies(): Flow<List<NowPlayingMovieEntity>>



}