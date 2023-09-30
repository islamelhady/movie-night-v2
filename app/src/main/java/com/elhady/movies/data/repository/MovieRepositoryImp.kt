package com.elhady.movies.data.repository

import com.elhady.movies.data.AppConfiguration
import com.elhady.movies.data.database.daos.MovieDao
import com.elhady.movies.data.database.entity.PopularMovieEntity
import com.elhady.movies.data.remote.State
import com.elhady.movies.data.remote.response.BaseResponse
import com.elhady.movies.data.remote.response.MovieDto
import com.elhady.movies.data.remote.response.PersonDto
import com.elhady.movies.data.remote.response.genre.GenreDto
import com.elhady.movies.data.remote.service.MovieService
import com.elhady.movies.domain.mappers.MovieMapper
import com.elhady.movies.domain.models.PopularMovie
import com.elhady.movies.utilities.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import java.lang.Exception
import java.util.Date
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
    private val movieService: MovieService,
    private val popularMovieMapper: MovieMapper,
    private val movieDao: MovieDao,
    private val appConfiguration: AppConfiguration
) :
    MovieRepository {

    override suspend fun getPopularMovies(): Flow<List<PopularMovieEntity>>{
        refreshOneTimePerDay(
            appConfiguration.getRequestDate(Constants.POPULAR_MOVIE_REQUEST_DATE_KEY),
            ::refreshPopularMovies
        )
        return movieDao.getPopularMovies()
    }

    suspend fun refreshPopularMovies(currentDate: Date){
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
                appConfiguration.saveRequestDate(Constants.POPULAR_MOVIE_REQUEST_DATE_KEY, currentDate.time)
            }
        )
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

    protected suspend fun <T, E> wrap(
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

    private suspend fun refreshOneTimePerDay(
        requestDate:Long?,
        refreshData :  suspend (Date) -> Unit){
        val currentDate = Date()
        if (requestDate != null) {
            if (Date(requestDate).after(currentDate)) {
                refreshData(currentDate)
            }
        } else {
            refreshData(currentDate)
        }
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