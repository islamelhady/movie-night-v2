package com.elhady.movies.data.repository

import com.elhady.movies.data.remote.State
import com.elhady.movies.data.remote.response.BaseResponse
import com.elhady.movies.data.remote.response.MovieDto
import com.elhady.movies.data.remote.response.PersonDto
import com.elhady.movies.data.remote.response.genre.GenreDto
import com.elhady.movies.data.remote.response.genre.GenreResponse
import com.elhady.movies.domain.models.PopularMovie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getPopularMovies(): Flow<List<MovieDto>>

    fun getUpcomingMovies(): Flow<State<BaseResponse<MovieDto>>>

    fun getTopRatedMovies(): Flow<State<BaseResponse<MovieDto>>>

    fun getNowPlayingMovies(): Flow<State<BaseResponse<MovieDto>>>

    fun getTrendingPerson(): Flow<State<BaseResponse<PersonDto>>>

    fun getTrendingMovie(): Flow<State<BaseResponse<MovieDto>>>

    suspend fun getGenreMovies(): List<GenreDto>?

    suspend fun getRefreshPopular(): Flow<List<PopularMovie>>
}