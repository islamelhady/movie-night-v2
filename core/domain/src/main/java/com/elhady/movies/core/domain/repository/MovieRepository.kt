package com.elhady.movies.core.domain.repository

import androidx.paging.Pager
import com.elhady.movies.core.domain.model.*
import com.elhady.movies.core.domain.model.moviedetails.*

interface MovieRepository {

    // Discovery / Popular / Trending Movies
    suspend fun getPopularMoviesPaging(): Pager<Int, MovieEntity>
    suspend fun getTopRateMoviesPaging(): Pager<Int, MovieEntity>
    suspend fun getTrendingMoviesPaging(): Pager<Int, MovieEntity>
    suspend fun getPopularMoviesFromDatabase(): List<MovieEntity>
    suspend fun getPopularMoviesFromRemote(): List<MovieEntity>
    suspend fun refreshPopularMovies()

    suspend fun getNowPlayingMovies(): List<MovieEntity>
    suspend fun refreshNowPlayingMovies()

    suspend fun getTopRatedMovies(): List<MovieEntity>
    suspend fun refreshTopRatedMovies()

    suspend fun getUpcomingMoviesFromDatabase(): List<MovieEntity>
    suspend fun refreshUpcomingMovies()

    suspend fun getTrendingMovies(): List<MovieEntity>
    suspend fun refreshTrendingMovies()

    // Movie Details & Reviews
    suspend fun getMoviesDetails(movieId: Int): MovieDetailsEntity
    suspend fun getMovieReviews(movieId: Int, page: Int): ReviewResponseEntity
    suspend fun getTrailerVideoForMovie(movieID: Int): YoutubeVideoDetailsEntity

    // Global Refresh Logic
    suspend fun getLastRefreshTime(): Long?
    suspend fun setLastRefreshTime(time: Long)
    suspend fun refreshAll()
}
