package com.elhady.movies.data.remote.repository

import com.elhady.movies.data.remote.State
import com.elhady.movies.data.remote.response.MovieResponse
import com.elhady.movies.data.remote.service.MovieService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.lang.Exception

class MovieRepositoryImp(val movieService: MovieService) : MovieRepository {
    override fun getPopularMovies(): Flow<State<MovieResponse>> {
       return wrapWithFlow { movieService.getPopularMovies() }
    }

    override fun getUpcomingMovies(): Flow<State<MovieResponse>> {
        return wrapWithFlow { movieService.getUpcomingMovies() }
    }

    override fun getTopRatedMovies(): Flow<State<MovieResponse>> {
        return wrapWithFlow { movieService.getTopRatedMovies() }
    }

    override fun getNowPlayingMovies(): Flow<State<MovieResponse>> {
        return wrapWithFlow { movieService.getNowPlayingMovies() }
    }

    private fun <T> wrapWithFlow(function: suspend () -> Response<T>): Flow<State<T>> {
        return flow {
            emit(State.Loading)
            try {
                val response = function()
                if (response.isSuccessful) {
                    emit(State.Success(response.body()))
                } else {
                    emit(State.Error(response.message()))
                }
            } catch (e: Exception) {
                emit(State.Error(e.message.toString()))
            }
        }
    }
}