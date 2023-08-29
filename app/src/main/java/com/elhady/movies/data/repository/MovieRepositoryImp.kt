package com.elhady.movies.data.repository

import com.elhady.movies.data.remote.State
import com.elhady.movies.data.remote.response.BaseResponse
import com.elhady.movies.data.remote.response.MovieDto
import com.elhady.movies.data.remote.response.PersonDto
import com.elhady.movies.data.remote.service.MovieService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Dispatcher
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(private val movieService: MovieService) :
    MovieRepository {
    override fun getPopularMovies(): Flow<State<BaseResponse<MovieDto>>> {
       return wrapWithFlow { movieService.getPopularMovies() }.flowOn(Dispatchers.IO)
    }

    override fun getUpcomingMovies(): Flow<State<BaseResponse<MovieDto>>> {
        return wrapWithFlow { movieService.getUpcomingMovies() }
    }

    override fun getTopRatedMovies(): Flow<State<BaseResponse<MovieDto>>> {
        return wrapWithFlow { movieService.getTopRatedMovies() }
    }

    override fun getNowPlayingMovies(): Flow<State<BaseResponse<MovieDto>>> {
        return wrapWithFlow { movieService.getNowPlayingMovies() }
    }

    override fun getTrendingPerson(): Flow<State<BaseResponse<PersonDto>>> {
        return wrapWithFlow {movieService.getTrendingPerson()}
    }

    override fun getTrendingMovie(): Flow<State<BaseResponse<MovieDto>>> {
        return wrapWithFlow { movieService.getTrendingMovie() }
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