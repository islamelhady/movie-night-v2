package com.elhady.movies.core.domain.repository

import androidx.paging.Pager
import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.core.domain.model.movie.MovieDetails
import com.elhady.movies.core.domain.model.movie.ReviewResponse
import com.elhady.movies.core.domain.model.common.YoutubeVideoDetails

interface MovieRepository {

    // Discovery / Popular / Trending Movies
    suspend fun getPopularMoviesPaging(): Pager<Int, Movie>
    suspend fun getTopRateMoviesPaging(): Pager<Int, Movie>
    suspend fun getTrendingMoviesPaging(): Pager<Int, Movie>
    suspend fun getPopularMoviesFromDatabase(): List<Movie>
    suspend fun getPopularMoviesFromRemote(): List<Movie>
    suspend fun refreshPopularMovies()

    suspend fun getNowPlayingMovies(): List<Movie>
    suspend fun refreshNowPlayingMovies()

    suspend fun getTopRatedMovies(): List<Movie>
    suspend fun refreshTopRatedMovies()

    suspend fun getUpcomingMoviesFromDatabase(): List<Movie>
    suspend fun refreshUpcomingMovies()

    suspend fun getTrendingMovies(): List<Movie>
    suspend fun refreshTrendingMovies()

    // Movie Details & Reviews
    suspend fun getMoviesDetails(movieId: Int): MovieDetails
    suspend fun getMovieReviews(movieId: Int, page: Int): ReviewResponse
    suspend fun getTrailerVideoForMovie(movieID: Int): YoutubeVideoDetails

    // Global Refresh Logic
    suspend fun getLastRefreshTime(): Long?
    suspend fun setLastRefreshTime(time: Long)
    suspend fun refreshAll()
}
