package com.elhady.movies.data.remote.repository

import com.elhady.movies.data.remote.State
import com.elhady.movies.data.remote.response.MovieResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getPopularMovies(): Flow<State<MovieResponse>>

    fun getUpcomingMovies(): Flow<State<MovieResponse>>

    fun getTopRatedMovies(): Flow<State<MovieResponse>>

    fun getNowPlayingMovies(): Flow<State<MovieResponse>>
}