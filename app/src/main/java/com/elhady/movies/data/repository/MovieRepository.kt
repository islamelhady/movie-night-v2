package com.elhady.movies.data.repository

import com.elhady.movies.data.local.database.entity.PopularMovieEntity
import com.elhady.movies.data.local.database.entity.TrendingMovieEntity
import com.elhady.movies.data.remote.State
import com.elhady.movies.data.remote.response.BaseResponse
import com.elhady.movies.data.remote.response.MovieDto
import com.elhady.movies.data.remote.response.PersonDto
import com.elhady.movies.data.remote.response.genre.GenreDto
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getPopularMovies(): Flow<List<PopularMovieEntity>>

    fun getUpcomingMovies(): Flow<State<BaseResponse<MovieDto>>>

    fun getTopRatedMovies(): Flow<State<BaseResponse<MovieDto>>>

    fun getNowPlayingMovies(): Flow<State<BaseResponse<MovieDto>>>

    fun getTrendingPerson(): Flow<State<BaseResponse<PersonDto>>>

    suspend fun getTrendingMovie(): Flow<List<TrendingMovieEntity>>

    suspend fun getGenreMovies(): List<GenreDto>?

}