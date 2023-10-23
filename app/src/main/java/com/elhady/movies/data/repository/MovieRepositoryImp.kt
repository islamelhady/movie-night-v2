package com.elhady.movies.data.repository

import androidx.paging.Pager
import com.elhady.movies.data.Constant
import com.elhady.movies.data.local.AppConfiguration
import com.elhady.movies.data.local.database.daos.MovieDao
import com.elhady.movies.data.local.database.entity.movies.AdventureMovieEntity
import com.elhady.movies.data.local.database.entity.movies.MysteryMovieEntity
import com.elhady.movies.data.local.database.entity.movies.NowPlayingMovieEntity
import com.elhady.movies.data.local.database.entity.movies.PopularMovieEntity
import com.elhady.movies.data.local.database.entity.movies.TopRatedMovieEntity
import com.elhady.movies.data.local.database.entity.movies.TrendingMovieEntity
import com.elhady.movies.data.local.mappers.movies.TrendingMovieMapper
import com.elhady.movies.data.local.database.entity.movies.UpcomingMovieEntity
import com.elhady.movies.data.local.mappers.movies.AdventureMoviesMapper
import com.elhady.movies.data.local.mappers.movies.MysteryMoviesMapper
import com.elhady.movies.data.local.mappers.movies.NowPlayingMovieMapper
import com.elhady.movies.data.local.mappers.movies.UpcomingMovieMapper
import com.elhady.movies.data.remote.response.genre.GenreDto
import com.elhady.movies.data.remote.service.MovieService
import com.elhady.movies.data.local.mappers.movies.PopularMovieMapper
import com.elhady.movies.data.local.mappers.movies.TopRatedMovieMapper
import com.elhady.movies.data.remote.response.MovieDto
import com.elhady.movies.data.repository.mediaDataSource.movies.AdventureMovieDataSource
import com.elhady.movies.data.repository.mediaDataSource.movies.MovieDataSourceContainer
import com.elhady.movies.data.repository.mediaDataSource.movies.MysteryMovieDataSource
import com.elhady.movies.data.repository.mediaDataSource.movies.NowPlayingMovieDataSource
import com.elhady.movies.data.repository.mediaDataSource.movies.TrendingMovieDataSource
import com.elhady.movies.data.repository.mediaDataSource.movies.UpcomingMovieDataSource
import com.elhady.movies.utilities.Constants
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
    private val movieService: MovieService,
    private val popularMovieMapper: PopularMovieMapper,
    private val movieDao: MovieDao,
    private val appConfiguration: AppConfiguration,
    private val trendingMovieMapper: TrendingMovieMapper,
    private val upcomingMovieMapper: UpcomingMovieMapper,
    private val nowPlayingMovieMapper: NowPlayingMovieMapper,
    private val topRatedMovieMapper: TopRatedMovieMapper,
    private val mysteryMoviesMapper: MysteryMoviesMapper,
    private val adventureMoviesMapper: AdventureMoviesMapper,
    private val movieDataSourceContainer: MovieDataSourceContainer
) : MovieRepository, BaseRepository() {

    /**
     *  Popular Movies
     */
    override suspend fun getPopularMovies(): Flow<List<PopularMovieEntity>> {
        refreshOneTimePerDay(
            appConfiguration.getRequestDate(Constant.POPULAR_MOVIE_REQUEST_DATE_KEY),
            ::refreshPopularMovies
        )
        return movieDao.getPopularMovies()
    }

    private suspend fun refreshPopularMovies(currentDate: Date) {
        val genre = getGenreMovies() ?: emptyList()
        wrap(
            { movieService.getPopularMovies() },
            { list ->
                list?.map {
                    popularMovieMapper.map(it, genre)
                }
            },
            {
                movieDao.insertPopularMovie(it)
                appConfiguration.saveRequestDate(
                    Constant.POPULAR_MOVIE_REQUEST_DATE_KEY,
                    currentDate.time
                )
            }
        )
    }

    /**
     *  Genre Movies
     */
    override suspend fun getGenreMovies(): List<GenreDto>? {
        return movieService.getGenreMovies().body()?.genres
    }

    /**
     *  Upcoming Movies
     */

    override suspend fun getUpcomingMovies(): Flow<List<UpcomingMovieEntity>> {
        refreshOneTimePerDay(
            appConfiguration.getRequestDate(Constant.UPCOMING_MOVIE_REQUEST_DATE_KEY),
            ::refreshUpcomingMovies
        )
        return movieDao.getUpcomingMovies()
    }

    suspend fun refreshUpcomingMovies(currentDate: Date) {
        wrap(
            { movieService.getUpcomingMovies() },
            { list ->
                list?.map {
                    upcomingMovieMapper.map(it)
                }
            },
            {
                movieDao.deleteUpcomingMovies()
                movieDao.insertUpcomingMovie(it)
                appConfiguration.saveRequestDate(
                    Constant.UPCOMING_MOVIE_REQUEST_DATE_KEY,
                    currentDate.time
                )
            })
    }

    /**
     *  All Popular Movies
     */
    override fun getAllUpcomingMovies(): Pager<Int, MovieDto> {
        return Pager(config = pagingConfig, pagingSourceFactory = { movieDataSourceContainer.upcomingMovieDataSource })
    }

    /**
     *  Top Rated Movies
     */
    override suspend fun getTopRatedMovies(): Flow<List<TopRatedMovieEntity>> {
        refreshOneTimePerDay(
            appConfiguration.getRequestDate(Constant.TOP_RATED_MOVIE_REQUEST_DATE_KEY),
            ::refreshTopRatedMovies
        )
        return movieDao.getTopRatedMovies()
    }

    suspend fun refreshTopRatedMovies(currentDate: Date) {
        wrap(
            { movieService.getTopRatedMovies() },
            { list ->
                list?.map { topRatedMovieMapper.map(it) }
            },
            {
                movieDao.deleteTopRatedMovies()
                movieDao.insertTopRatedMovies(it)
                appConfiguration.saveRequestDate(
                    Constant.TOP_RATED_MOVIE_REQUEST_DATE_KEY,
                    currentDate.time
                )
            }
        )
    }

    /**
     *  Now Playing Movies
     */
    override suspend fun getNowPlayingMovies(): Flow<List<NowPlayingMovieEntity>> {
        refreshOneTimePerDay(
            appConfiguration.getRequestDate(Constant.NOW_PLAYING_MOVIE_REQUEST_DATE_KEY),
            ::refreshNowPlayingMovies
        )
        return movieDao.getNowPlayingMovies()
    }

    private suspend fun refreshNowPlayingMovies(currentDate: Date) {
        wrap(
            { movieService.getNowPlayingMovies() },
            { list ->
                list?.map { nowPlayingMovieMapper.map(it) }
            },
            {
                movieDao.deleteNowPlayingMovies()
                movieDao.insertNowPlayingMovies(it)
                appConfiguration.saveRequestDate(
                    Constant.NOW_PLAYING_MOVIE_REQUEST_DATE_KEY,
                    currentDate.time
                )
            }
        )
    }

    /**
     *  All Trending Movies
     */

    override fun getAllNowPlayingMovies(): Pager<Int, MovieDto> {
        return Pager(config = pagingConfig, pagingSourceFactory = { movieDataSourceContainer.nowPlayingMovieDataSource })
    }

    /**
     *  Trending Movies
     */
    override suspend fun getTrendingMovie(): Flow<List<TrendingMovieEntity>> {
        refreshOneTimePerDay(
            appConfiguration.getRequestDate(Constant.TRENDING_MOVIE_REQUEST_DATE_KEY),
            ::refreshTrendingMovies
        )
        return movieDao.getAllTrendingMovies()
    }

    suspend fun refreshTrendingMovies(currentDate: Date) {
        wrap(
            {
                movieService.getTrendingMovie()
            },
            { list ->
                list?.map {
                    trendingMovieMapper.map(it)
                }
            },
            {
                movieDao.deleteTrendingMovies()
                movieDao.insertTrendingMovies(it)
                appConfiguration.saveRequestDate(
                    Constant.TRENDING_MOVIE_REQUEST_DATE_KEY,
                    currentDate.time
                )
            }
        )
    }

    /**
     *  All Trending Movies
     */
    override fun getAllTrendingMovies(): Pager<Int, MovieDto> {
        return Pager(config = pagingConfig, pagingSourceFactory = { movieDataSourceContainer.trendingMovieDataSource })
    }

    /**
     *  Mystery Movies
     */
    override suspend fun getMysteryMovies(): Flow<List<MysteryMovieEntity>> {
        refreshOneTimePerDay(
            appConfiguration.getRequestDate(Constant.MYSTERY_MOVIE_REQUEST_DATE_KEY),
            ::refreshMysteryMovies
        )
        return movieDao.getMysteryMovies()
    }

    suspend fun refreshMysteryMovies(currentDate: Date) {
        wrap(
            { movieService.getMoviesListByGenre(genreID = Constants.MYSTERY_ID) },
            { list ->
                list?.map {
                    mysteryMoviesMapper.map(it)
                }
            },
            {
                movieDao.deleteMysteryMovies()
                movieDao.insertMysteryMovies(it)
                appConfiguration.saveRequestDate(
                    Constant.MYSTERY_MOVIE_REQUEST_DATE_KEY,
                    currentDate.time
                )
            }
        )
    }

    /**
     *  All Mystery Movies
     */

    override fun getAllMysteryMovies(): Pager<Int, MovieDto> {
        return Pager(config = pagingConfig, pagingSourceFactory = { movieDataSourceContainer.mysteryMovieDataSource })
    }

    /**
     *  Adventure Movies
     */
    override suspend fun getAdventureMovies(): Flow<List<AdventureMovieEntity>> {
        refreshOneTimePerDay(
            appConfiguration.getRequestDate(Constant.ADVENTURE_MOVIE_REQUEST_DATE_KEY),
            ::refreshAdventureMovies
        )
        return movieDao.getAdventureMovies()
    }

    suspend fun refreshAdventureMovies(currentDate: Date) {
        wrap(
            { movieService.getMoviesListByGenre(genreID = Constants.ADVENTURE_ID) },
            { list ->
                list?.map {
                    adventureMoviesMapper.map(it)
                }
            },
            {
                movieDao.deleteAdventureMovies()
                movieDao.insertAdventureMovies(it)
                appConfiguration.saveRequestDate(
                    Constant.ADVENTURE_MOVIE_REQUEST_DATE_KEY,
                    currentDate.time
                )
            }
        )
    }

    /**
     *  All Adventure Movies
     */
    override fun getAllAdventureMovies(): Pager<Int, MovieDto> {
        return Pager(config = pagingConfig, pagingSourceFactory = { movieDataSourceContainer.adventureMovieDataSource })
    }

}