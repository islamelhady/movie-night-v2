package com.elhady.movies.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.elhady.movies.core.data.base.BaseRepository
import com.elhady.movies.core.data.mapper.common.DomainYoutubeDetailsMapper
import com.elhady.movies.core.data.mapper.movie.DomainMovieDetailsMapper
import com.elhady.movies.core.data.mapper.movie.DomainMovieMapper
import com.elhady.movies.core.data.mapper.movie.DomainNowPlayingMovieMapper
import com.elhady.movies.core.data.mapper.movie.DomainPopularMovieMapper
import com.elhady.movies.core.data.mapper.movie.DomainReviewsMapper
import com.elhady.movies.core.data.mapper.movie.DomainTopRatedMovieMapper
import com.elhady.movies.core.data.mapper.movie.DomainTrendingMoviesMapper
import com.elhady.movies.core.data.mapper.movie.DomainUpcomingMovieMapper
import com.elhady.movies.core.data.mapper.movie.LocalNowPlayingMovieMapper
import com.elhady.movies.core.data.mapper.movie.LocalPopularMovieMapper
import com.elhady.movies.core.data.mapper.movie.LocalTopRatedMovieMapper
import com.elhady.movies.core.data.mapper.movie.LocalTrendingMoviesMapper
import com.elhady.movies.core.data.mapper.movie.LocalUpcomingMovieMapper
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
    private val localPopularMovieMapper: LocalPopularMovieMapper,
    private val localNowPlayingMovieMapper: LocalNowPlayingMovieMapper,
    private val localTopRatedMovieMapper: LocalTopRatedMovieMapper,
    private val localUpcomingMovieMapper: LocalUpcomingMovieMapper,
    private val localTrendingMoviesMapper: LocalTrendingMoviesMapper,
    private val domainPopularMovieMapper: DomainPopularMovieMapper,
    private val domainNowPlayingMovieMapper: DomainNowPlayingMovieMapper,
    private val domainTopRatedMovieMapper: DomainTopRatedMovieMapper,
    private val domainUpcomingMovieMapper: DomainUpcomingMovieMapper,
    private val domainTrendingMovieMapper: DomainTrendingMoviesMapper,
    private val domainMovieMapper: DomainMovieMapper,
    private val domainMovieDetailsMapper: DomainMovieDetailsMapper,
    private val domainReviewsMapper: DomainReviewsMapper,
    private val domainYoutubeDetailsMapper: DomainYoutubeDetailsMapper,
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
        return domainPopularMovieMapper.map(popularMovieDao.getPopularMovies())
    }

    override suspend fun getPopularMoviesFromRemote(): List<Movie> {
        val page = random.nextInt(500) + 1
        val moviesDtos =
            wrapApiCall { movieApiService.getPopularMovies(page = page) }.results?.filterNotNull()
                ?: emptyList()
        val genres = genreRepository.getGenresMovies()
        return domainMovieMapper.map(moviesDtos, genres)
    }

    override suspend fun refreshPopularMovies() {
        refreshWrapper(
            apiCall = { movieApiService.getPopularMovies(random.nextInt(20) + 1) },
            localMapper = localPopularMovieMapper::map,
            databaseSaver = popularMovieDao::insertPopularMovies,
            clearOldLocalData = popularMovieDao::clearAllPopularMovies
        )
    }

    override suspend fun getNowPlayingMovies(): List<Movie> {
        return domainNowPlayingMovieMapper.map(nowPlayingMovieDao.getNowPlayingMovies())
    }

    override suspend fun refreshNowPlayingMovies() {
        refreshWrapper(
            { movieApiService.getNowPlayingMovies(random.nextInt(20) + 1) },
            localNowPlayingMovieMapper::map,
            nowPlayingMovieDao::insertNowPlayingMovies,
            clearOldLocalData = nowPlayingMovieDao::clearAllNowPlayingMovies
        )
    }

    override suspend fun getTopRatedMovies(): List<Movie> {
        return domainTopRatedMovieMapper.map(topRatedMovieDao.getTopRatedMovies())
    }

    override suspend fun refreshTopRatedMovies() {
        refreshWrapper(
            { movieApiService.getTopRatedMovies(random.nextInt(20) + 1) },
            localTopRatedMovieMapper::map,
            topRatedMovieDao::insertTopRatedMovies,
            clearOldLocalData = topRatedMovieDao::clearAllTopRatedMovies
        )
    }

    override suspend fun getUpcomingMoviesFromDatabase(): List<Movie> {
        return domainUpcomingMovieMapper.map(upcomingMovieDao.getUpcomingMovies())
    }

    override suspend fun refreshUpcomingMovies() {
        val genres = genreRepository.getGenresMovies()
        refreshWrapper(
            apiCall = movieApiService::getUpcomingMovies,
            localMapper = { localUpcomingMovieMapper.map(it, genres) },
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
            localMapper = { localTrendingMoviesMapper.map(it, genres) },
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
        return domainMovieDetailsMapper.map(wrapApiCall { movieApiService.getMovieDetails(movieId) })
    }

    override suspend fun getMovieReviews(movieId: Int, page: Int): ReviewResponse {
        return domainReviewsMapper.map(wrapApiCall { movieApiService.getMovieReviews(movieId, page) })
    }

    override suspend fun getTrailerVideoForMovie(movieID: Int): YoutubeVideoDetails {
        val call =
            wrapApiCall { movieApiService.getTrailerVideoForMovie(movieID) }.results?.first()
                ?: YoutubeVideoDetailsDto()
        return domainYoutubeDetailsMapper.map(call)
    }
    // endregion
}
