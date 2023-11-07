package com.elhady.movies.data.repository

import androidx.paging.Pager
import com.elhady.movies.data.local.database.entity.movies.AdventureMovieEntity
import com.elhady.movies.data.local.database.entity.movies.MysteryMovieEntity
import com.elhady.movies.data.local.database.entity.movies.NowPlayingMovieEntity
import com.elhady.movies.data.local.database.entity.movies.PopularMovieEntity
import com.elhady.movies.data.local.database.entity.movies.TopRatedMovieEntity
import com.elhady.movies.data.local.database.entity.movies.TrendingMovieEntity
import com.elhady.movies.data.local.database.entity.movies.UpcomingMovieEntity
import com.elhady.movies.data.remote.response.CreditsDto
import com.elhady.movies.data.remote.response.movie.MovieDto
import com.elhady.movies.data.remote.response.genre.GenreDto
import com.elhady.movies.data.remote.response.movie.MovieDetailsDto
import com.elhady.movies.data.remote.response.review.ReviewDto
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getPopularMovies(): Flow<List<PopularMovieEntity>>

    suspend fun getUpcomingMovies(): Flow<List<UpcomingMovieEntity>>

    fun getAllUpcomingMovies(): Pager<Int, MovieDto>

    suspend fun getTopRatedMovies(): Flow<List<TopRatedMovieEntity>>

    fun getAllTopRatedMovies(): Pager<Int, MovieDto>

    suspend fun getNowPlayingMovies(): Flow<List<NowPlayingMovieEntity>>

    fun getAllNowPlayingMovies(): Pager<Int, MovieDto>

    suspend fun getMysteryMovies(): Flow<List<MysteryMovieEntity>>

    fun getAllMysteryMovies(): Pager<Int, MovieDto>

    suspend fun getAdventureMovies(): Flow<List<AdventureMovieEntity>>

    fun getAllAdventureMovies(): Pager<Int, MovieDto>

    suspend fun getTrendingMovie(): Flow<List<TrendingMovieEntity>>

    fun getAllTrendingMovies(): Pager<Int, MovieDto>

    fun getMoviesByGenre(genreId: Int): Pager<Int, MovieDto>
    fun getAllMovies(): Pager<Int, MovieDto>

    suspend fun getGenreMovies(): List<GenreDto>?

    suspend fun getDetailsMovies(movieId: Int): MovieDetailsDto?

    suspend fun getMovieCast(movieId: Int): CreditsDto?

    suspend fun getSimilarMovies(movieId: Int): List<MovieDto>?

    suspend fun getMovieReview(movieId: Int): List<ReviewDto>?

}