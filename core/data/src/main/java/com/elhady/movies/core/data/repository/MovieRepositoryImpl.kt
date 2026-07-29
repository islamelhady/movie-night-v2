package com.elhady.movies.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.elhady.movies.core.data.base.BaseRepository
import com.elhady.movies.core.data.mapper.common.YoutubeDetailsDtoMapper
import com.elhady.movies.core.data.mapper.movie.MovieDetailsDtoMapper
import com.elhady.movies.core.data.mapper.movie.MovieDtoMapper
import com.elhady.movies.core.data.mapper.movie.NowPlayingMovieEntityMapper
import com.elhady.movies.core.data.mapper.movie.PopularMovieEntityMapper
import com.elhady.movies.core.data.mapper.movie.ReviewsDtoMapper
import com.elhady.movies.core.data.mapper.movie.TopRatedMovieEntityMapper
import com.elhady.movies.core.data.mapper.movie.TrendingMoviesEntityMapper
import com.elhady.movies.core.data.mapper.movie.UpcomingMovieEntityMapper
import com.elhady.movies.core.data.mapper.movie.NowPlayingMovieDtoToEntityMapper
import com.elhady.movies.core.data.mapper.movie.PopularMovieDtoToEntityMapper
import com.elhady.movies.core.data.mapper.movie.TopRatedMovieDtoToEntityMapper
import com.elhady.movies.core.data.mapper.movie.TrendingMoviesDtoToEntityMapper
import com.elhady.movies.core.data.mapper.movie.UpcomingMovieDtoToEntityMapper
import com.elhady.movies.core.data.paging.movie.PopularMoviesShowMorePagingSource
import com.elhady.movies.core.data.paging.movie.TopRatedShowMorePagingSource
import com.elhady.movies.core.data.paging.movie.TrendingShowMorePagingSource
import com.elhady.movies.core.database.dao.movie.NowPlayingMovieDao
import com.elhady.movies.core.database.dao.movie.PopularMovieDao
import com.elhady.movies.core.database.dao.movie.TopRatedMovieDao
import com.elhady.movies.core.database.dao.movie.TrendingMovieDao
import com.elhady.movies.core.database.dao.movie.UpcomingMovieDao
import com.elhady.movies.core.datastore.local.PreferenceStorage
import com.elhady.movies.core.domain.model.common.YoutubeVideoDetails
import com.elhady.movies.core.domain.model.movie.MovieDetails
import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.core.domain.model.movie.ReviewResponse
import com.elhady.movies.core.domain.repository.GenreRepository
import com.elhady.movies.core.domain.repository.MovieRepository
import com.elhady.movies.core.domain.repository.PeopleRepository
import com.elhady.movies.core.network.api.MovieApiService
import com.elhady.movies.core.network.dto.common.YoutubeVideoDetailsDto
import java.util.Random
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApiService: MovieApiService,
    private val popularMovieDao: PopularMovieDao,
    private val nowPlayingMovieDao: NowPlayingMovieDao,
    private val topRatedMovieDao: TopRatedMovieDao,
    private val upcomingMovieDao: UpcomingMovieDao,
    private val trendingMovieDao: TrendingMovieDao,
    private val preferenceStorage: PreferenceStorage,
    private val genreRepository: GenreRepository,
    private val peopleRepository: PeopleRepository,
    private val popularMovieDtoToEntityMapper: PopularMovieDtoToEntityMapper,
    private val nowPlayingMovieDtoToEntityMapper: NowPlayingMovieDtoToEntityMapper,
    private val topRatedMovieDtoToEntityMapper: TopRatedMovieDtoToEntityMapper,
    private val upcomingMovieDtoToEntityMapper: UpcomingMovieDtoToEntityMapper,
    private val trendingMoviesDtoToEntityMapper: TrendingMoviesDtoToEntityMapper,
    private val popularMovieEntityMapper: PopularMovieEntityMapper,
    private val nowPlayingMovieEntityMapper: NowPlayingMovieEntityMapper,
    private val topRatedMovieEntityMapper: TopRatedMovieEntityMapper,
    private val upcomingMovieEntityMapper: UpcomingMovieEntityMapper,
    private val domainTrendingMovieMapper: TrendingMoviesEntityMapper,
    private val movieDtoMapper: MovieDtoMapper,
    private val movieDetailsDtoMapper: MovieDetailsDtoMapper,
    private val reviewsDtoMapper: ReviewsDtoMapper,
    private val domainYoutubeDetailsMapper: YoutubeDetailsDtoMapper,
    private val popularMovieMapperShowMore: PopularMoviesShowMorePagingSource,
    private val topRatedShowMorePagingSource: TopRatedShowMorePagingSource,
    private val trendingShowMorePagingSource: TrendingShowMorePagingSource,
    private val random: Random
) : BaseRepository(), MovieRepository {

    // region showMore
    override suspend fun getPopularMoviesPaging(): Pager<Int, Movie> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { popularMovieMapperShowMore }
        )
    }

    override suspend fun getTopRateMoviesPaging(): Pager<Int, Movie> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { topRatedShowMorePagingSource }
        )
    }

    override suspend fun getTrendingMoviesPaging(): Pager<Int, Movie> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { trendingShowMorePagingSource }
        )
    }
    // endregion

    // region movies
    override suspend fun getPopularMoviesFromDatabase(): List<Movie> {
        return popularMovieEntityMapper.map(popularMovieDao.getPopularMovies())
    }

    override suspend fun getPopularMoviesFromRemote(): List<Movie> {
        val page = random.nextInt(500) + 1
        val moviesDtos =
            wrapApiCall { movieApiService.getPopularMovies(page = page) }.results?.filterNotNull()
                ?: emptyList()
        val genres = genreRepository.getGenresMovies()
        return movieDtoMapper.map(moviesDtos, genres)
    }

    override suspend fun refreshPopularMovies() {
        refreshWrapper(
            apiCall = { movieApiService.getPopularMovies(random.nextInt(20) + 1) },
            localMapper = popularMovieDtoToEntityMapper::map,
            databaseSaver = popularMovieDao::insertPopularMovies,
            clearOldLocalData = popularMovieDao::clearAllPopularMovies
        )
    }

    override suspend fun getNowPlayingMovies(): List<Movie> {
        return nowPlayingMovieEntityMapper.map(nowPlayingMovieDao.getNowPlayingMovies())
    }

    override suspend fun refreshNowPlayingMovies() {
        refreshWrapper(
            { movieApiService.getNowPlayingMovies(random.nextInt(20) + 1) },
            nowPlayingMovieDtoToEntityMapper::map,
            nowPlayingMovieDao::insertNowPlayingMovies,
            clearOldLocalData = nowPlayingMovieDao::clearAllNowPlayingMovies
        )
    }

    override suspend fun getTopRatedMovies(): List<Movie> {
        return topRatedMovieEntityMapper.map(topRatedMovieDao.getTopRatedMovies())
    }

    override suspend fun refreshTopRatedMovies() {
        refreshWrapper(
            { movieApiService.getTopRatedMovies(random.nextInt(20) + 1) },
            topRatedMovieDtoToEntityMapper::map,
            topRatedMovieDao::insertTopRatedMovies,
            clearOldLocalData = topRatedMovieDao::clearAllTopRatedMovies
        )
    }

    override suspend fun getUpcomingMoviesFromDatabase(): List<Movie> {
        return upcomingMovieEntityMapper.map(upcomingMovieDao.getUpcomingMovies())
    }

    override suspend fun refreshUpcomingMovies() {
        val genres = genreRepository.getGenresMovies()
        refreshWrapper(
            apiCall = movieApiService::getUpcomingMovies,
            localMapper = { upcomingMovieDtoToEntityMapper.map(it, genres) },
            databaseSaver = upcomingMovieDao::insertUpcomingMovies,
            clearOldLocalData = upcomingMovieDao::clearAllUpcomingMovies
        )
    }

    override suspend fun getTrendingMovies(): List<Movie> {
        return domainTrendingMovieMapper.map(trendingMovieDao.getTrendingMovies())
    }

    override suspend fun refreshTrendingMovies() {
        val genres = genreRepository.getGenresMovies()
        refreshWrapper(
            apiCall = movieApiService::getTrendingMovies,
            localMapper = { trendingMoviesDtoToEntityMapper.map(it, genres) },
            databaseSaver = trendingMovieDao::insertTrendingMovies,
            clearOldLocalData = trendingMovieDao::clearAllTrendingMovies
        )
    }
    // endregion

    // region refresh time
    override suspend fun getLastRefreshTime(): Long? {
        return preferenceStorage.lastRefreshTime
    }

    override suspend fun setLastRefreshTime(time: Long) {
        preferenceStorage.setLastRefreshTime(time)
    }

    override suspend fun refreshAll() {
        genreRepository.refreshGenres()
        genreRepository.refreshGenresTv()
        refreshPopularMovies()
        peopleRepository.refreshPopularPeople()
        refreshNowPlayingMovies()
        refreshTopRatedMovies()
        refreshTrendingMovies()
        refreshUpcomingMovies()
    }
    // endregion

    // region movies details
    override suspend fun getMoviesDetails(movieId: Int): MovieDetails {
        return movieDetailsDtoMapper.map(wrapApiCall { movieApiService.getMovieDetails(movieId) })
    }

    override suspend fun getMovieReviews(movieId: Int, page: Int): ReviewResponse {
        return reviewsDtoMapper.map(wrapApiCall { movieApiService.getMovieReviews(movieId, page) })
    }

    override suspend fun getTrailerVideoForMovie(movieID: Int): YoutubeVideoDetails {
        val call =
            wrapApiCall { movieApiService.getTrailerVideoForMovie(movieID) }.results?.first()
                ?: YoutubeVideoDetailsDto()
        return domainYoutubeDetailsMapper.map(call)
    }
    // endregion
}
