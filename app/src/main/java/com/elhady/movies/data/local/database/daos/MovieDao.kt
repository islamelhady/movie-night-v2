package com.elhady.movies.data.local.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elhady.movies.data.local.database.entity.NowPlayingMovieEntity
import com.elhady.movies.data.local.database.entity.PopularMovieEntity
import com.elhady.movies.data.local.database.entity.TopRatedMovieEntity
import com.elhady.movies.data.local.database.entity.TrendingMovieEntity
import com.elhady.movies.data.local.database.entity.UpcomingMovieEntity
import com.elhady.movies.data.remote.response.MovieDto
import dagger.hilt.InstallIn
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDao {

    /**
     * Popular Movies
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularMovie(items: List<PopularMovieEntity>)

    @Query("DELETE FROM POPULAR_MOVIE_TABLE")
    suspend fun deletePopularMovie()

    @Query("SELECT * FROM POPULAR_MOVIE_TABLE")
    fun getPopularMovies(): Flow<List<PopularMovieEntity>>

    /**
     * Trending Movies
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrendingMovies(items: List<TrendingMovieEntity>)

    @Query("DELETE FROM TRENDING_MOVIE_TABLE")
    suspend fun deleteTrendingMovies()

    @Query("SELECT * FROM TRENDING_MOVIE_TABLE")
    fun getAllTrendingMovies(): Flow<List<TrendingMovieEntity>>

    /**
     * Upcoming Movies
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpcomingMovie(items: List<UpcomingMovieEntity>)

    @Query("DELETE FROM UPCOMING_MOVIE_TABLE")
    suspend fun deleteUpcomingMovies()

    @Query("SELECT * FROM UPCOMING_MOVIE_TABLE")
    fun getUpcomingMovies(): Flow<List<UpcomingMovieEntity>>

    /**
     * Now Playing Movies
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNowPlayingMovies(items: List<NowPlayingMovieEntity>)

    @Query("DELETE FROM NOW_PLAYING_MOVIE_TABLE")
    suspend fun deleteNowPlayingMovies()

    @Query("SELECT * FROM NOW_PLAYING_MOVIE_TABLE")
    fun getNowPlayingMovies(): Flow<List<NowPlayingMovieEntity>>

    /**
     *  Top Rated Movies
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopRatedMovies(items: List<TopRatedMovieEntity>)

    @Query("DELETE FROM TOP_RATED_MOVIE_TABLE")
    suspend fun deleteTopRatedMovies()

    @Query("SELECT * FROM TOP_RATED_MOVIE_TABLE")
    fun getTopRatedMovies(): Flow<List<TopRatedMovieEntity>>

}