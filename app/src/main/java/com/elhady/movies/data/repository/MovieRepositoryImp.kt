package com.elhady.movies.data.repository

import com.elhady.movies.data.local.AppConfiguration
import com.elhady.movies.data.local.database.daos.MovieDao
import com.elhady.movies.data.local.database.entity.PopularMovieEntity
import com.elhady.movies.data.local.database.entity.TrendingMovieEntity
import com.elhady.movies.data.local.mappers.TrendingMovieMapper
import com.elhady.movies.data.local.database.entity.UpcomingMovieEntity
import com.elhady.movies.data.local.mappers.UpcomingMovieMapper
import com.elhady.movies.data.remote.State
import com.elhady.movies.data.remote.response.BaseResponse
import com.elhady.movies.data.remote.response.MovieDto
import com.elhady.movies.data.remote.response.PersonDto
import com.elhady.movies.data.remote.response.genre.GenreDto
import com.elhady.movies.data.remote.service.MovieService
import com.elhady.movies.domain.mappers.MovieMapper
import com.elhady.movies.utilities.Constants
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
    private val movieService: MovieService,
    private val popularMovieMapper: MovieMapper,
    private val movieDao: MovieDao,
    private val appConfiguration: AppConfiguration,
    private val trendingMovieMapper: TrendingMovieMapper,
    private val upcomingMovieMapper: UpcomingMovieMapper
) :
    MovieRepository, BaseRepository() {

    override suspend fun getPopularMovies(): Flow<List<PopularMovieEntity>> {
        refreshOneTimePerDay(
            appConfiguration.getRequestDate(Constants.POPULAR_MOVIE_REQUEST_DATE_KEY),
            ::refreshPopularMovies
        )
        return movieDao.getPopularMovies()
    }

    private suspend fun refreshPopularMovies(currentDate: Date) {
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
                appConfiguration.saveRequestDate(
                    Constants.POPULAR_MOVIE_REQUEST_DATE_KEY,
                    currentDate.time
                )
            }
        )
    }


    override suspend fun getUpcomingMovies(): Flow<List<UpcomingMovieEntity>> {
        refreshOneTimePerDay(
            appConfiguration.getRequestDate(Constants.UPCOMING_MOVIE_REQUEST_DATE_KEY),
            ::refreshUpcomingMovies
        )
        return movieDao.getUpcomingMovies()
    }

    suspend fun refreshUpcomingMovies(currentDate: Date) {
        wrap({
            movieService.getUpcomingMovies()
        }, { items ->
            items?.map {
                upcomingMovieMapper.map(it)
            }
        }, {
            movieDao.deleteUpcomingMovies()
            movieDao.insertUpcomingMovie(it)
            appConfiguration.saveRequestDate(
                Constants.UPCOMING_MOVIE_REQUEST_DATE_KEY,
                currentDate.time
            )
        })
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

    override suspend fun getTrendingMovie(): Flow<List<TrendingMovieEntity>> {
        refreshOneTimePerDay(
            appConfiguration.getRequestDate(Constants.TRENDING_MOVIE_REQUEST_DATE_KEY),
            ::refreshTrendingMovies
        )
        return movieDao.getAllTrendingMovies()
    }

    suspend fun refreshTrendingMovies(currentDate: Date) {
        wrap(
            {
                movieService.getTrendingMovie()
            },
            { items ->
                items?.map {
                    trendingMovieMapper.map(it)
                }
            },
            {
                movieDao.deleteTrendingMovies()
                movieDao.insertTrendingMovies(it)
                appConfiguration.saveRequestDate(
                    Constants.TRENDING_MOVIE_REQUEST_DATE_KEY,
                    currentDate.time
                )
            }
        )
    }

    override suspend fun getGenreMovies(): List<GenreDto>? {
        return movieService.getGenreMovies().body()?.genres
    }


}