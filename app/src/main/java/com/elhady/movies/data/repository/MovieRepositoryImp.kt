package com.elhady.movies.data.repository

import com.elhady.movies.data.database.daos.MovieDao
import com.elhady.movies.data.database.entity.PopularMovieEntity
import com.elhady.movies.data.remote.State
import com.elhady.movies.data.remote.response.BaseResponse
import com.elhady.movies.data.remote.response.MovieDto
import com.elhady.movies.data.remote.response.PersonDto
import com.elhady.movies.data.remote.response.genre.GenreDto
import com.elhady.movies.data.remote.service.MovieService
import com.elhady.movies.domain.mappers.MovieMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
    private val movieService: MovieService,
    private val popularMovieMapper: MovieMapper,
    private val movieDao: MovieDao
) :
    MovieRepository {

    override suspend fun getPopularMovies(): Flow<List<PopularMovieEntity>>{
        val genre = getGenreMovies() ?: emptyList()
        wrap(
            { movieService.getPopularMovies() },
            { items ->
                items?.map {
                    popularMovieMapper.map(it, genre)
                }
            },
            {
                movieDao.insertPopularMovie(it)
            }
        )
        return movieDao.getPopularMovies()
    }

    private suspend fun <T, E> wrap(
        request: suspend () -> Response<BaseResponse<T>>,
        mapper: (List<T>?) -> List<E>?,
        insertIntoDatabase: suspend (List<E>) -> Unit
    ) {
        val response = request()
            if (response.isSuccessful) {
                val items = response.body()?.items
                mapper(items)?.let {
                    insertIntoDatabase(it)
                }
            } else {
                throw Throwable()
        }
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
        return wrapWithFlow { movieService.getTrendingPerson() }
    }

    override fun getTrendingMovie(): Flow<State<BaseResponse<MovieDto>>> {
        return wrapWithFlow { movieService.getTrendingMovie() }
    }

    override suspend fun getGenreMovies(): List<GenreDto>? {
        return movieService.getGenreMovies().body()?.genres
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