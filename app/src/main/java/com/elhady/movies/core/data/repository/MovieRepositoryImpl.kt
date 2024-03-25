package com.elhady.movies.core.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.elhady.movies.core.data.local.PreferenceStorage
import com.elhady.movies.core.data.local.database.MovieDao
import com.elhady.movies.core.data.local.database.TvShowDao
import com.elhady.movies.core.data.local.database.dto.SearchHistoryLocalDto
import com.elhady.movies.core.data.local.database.dto.tvshow.TvShowsLocalDto
import com.elhady.movies.core.data.remote.request.AddMediaToListRequest
import com.elhady.movies.core.data.remote.request.CreateUserListRequest
import com.elhady.movies.core.data.remote.request.DeleteMovieRequest
import com.elhady.movies.core.data.remote.request.FavoriteRequest
import com.elhady.movies.core.data.remote.request.ListRequest
import com.elhady.movies.core.data.remote.request.RateRequest
import com.elhady.movies.core.data.remote.request.RatingEpisodeDetailsRequest
import com.elhady.movies.core.data.remote.request.RatingRequest
import com.elhady.movies.core.data.remote.request.WatchlistRequest
import com.elhady.movies.core.data.remote.response.dto.YoutubeVideoDetailsRemoteDto
import com.elhady.movies.core.data.remote.service.MovieService
import com.elhady.movies.core.data.repository.mappers.cash.LocalGenresMovieMapper
import com.elhady.movies.core.data.repository.mappers.cash.LocalGenresTvMapper
import com.elhady.movies.core.data.repository.mappers.cash.LocalPopularPeopleMapper
import com.elhady.movies.core.data.repository.mappers.cash.movie.LocalNowPlayingMovieMapper
import com.elhady.movies.core.data.repository.mappers.cash.movie.LocalPopularMovieMapper
import com.elhady.movies.core.data.repository.mappers.cash.movie.LocalTopRatedMovieMapper
import com.elhady.movies.core.data.repository.mappers.cash.movie.LocalTrendingMoviesMapper
import com.elhady.movies.core.data.repository.mappers.cash.movie.LocalUpcomingMovieMapper
import com.elhady.movies.core.data.repository.mappers.cash.tv.LocalAiringTodayTvShowMapper
import com.elhady.movies.core.data.repository.mappers.cash.tv.LocalTvShowMapper
import com.elhady.movies.core.data.repository.mappers.domain.DomainGenreMapper
import com.elhady.movies.core.data.repository.mappers.domain.DomainGenreTvMapper
import com.elhady.movies.core.data.repository.mappers.domain.DomainMovieDetailsMapper
import com.elhady.movies.core.data.repository.mappers.domain.DomainMoviesByPeopleMapper
import com.elhady.movies.core.data.repository.mappers.domain.DomainPeopleDetailsMapper
import com.elhady.movies.core.data.repository.mappers.domain.DomainPeopleMapper
import com.elhady.movies.core.data.repository.mappers.domain.DomainPeopleRemoteMapper
import com.elhady.movies.core.data.repository.mappers.domain.DomainReviewsMapper
import com.elhady.movies.core.data.repository.mappers.domain.DomainSeasonDetailsMapper
import com.elhady.movies.core.data.repository.mappers.domain.DomainStatusMapper
import com.elhady.movies.core.data.repository.mappers.domain.DomainTvDetailsCreditMapper
import com.elhady.movies.core.data.repository.mappers.domain.DomainTvDetailsMapper
import com.elhady.movies.core.data.repository.mappers.domain.DomainTvDetailsReviewMapper
import com.elhady.movies.core.data.repository.mappers.domain.DomainTvDetailsSeasonMapper
import com.elhady.movies.core.data.repository.mappers.domain.tv.DomainTvShowMapper
import com.elhady.movies.core.data.repository.mappers.domain.DomainTvShowsByPeopleMapper
import com.elhady.movies.core.data.repository.mappers.domain.DomainUserListsMapper
import com.elhady.movies.core.data.repository.mappers.domain.DomainYoutubeDetailsMapper
import com.elhady.movies.core.data.repository.mappers.domain.episode.DomainCastMapper
import com.elhady.movies.core.data.repository.mappers.domain.episode.DomainEpisodeDetailsMapper
import com.elhady.movies.core.data.repository.mappers.domain.episode.DomainRatingEpisodeMapper
import com.elhady.movies.core.data.repository.mappers.domain.movie.DomainMovieMapper
import com.elhady.movies.core.data.repository.mappers.domain.movie.DomainMyRatedMoviesDetailsMapper
import com.elhady.movies.core.data.repository.mappers.domain.movie.DomainNowPlayingMovieMapper
import com.elhady.movies.core.data.repository.mappers.domain.movie.DomainPopularMovieMapper
import com.elhady.movies.core.data.repository.mappers.domain.movie.DomainTopRatedMovieMapper
import com.elhady.movies.core.data.repository.mappers.domain.movie.DomainTrendingMoviesMapper
import com.elhady.movies.core.data.repository.mappers.domain.movie.DomainTvMapper
import com.elhady.movies.core.data.repository.mappers.domain.movie.DomainUpcomingMovieMapper
import com.elhady.movies.core.data.repository.mappers.domain.search.DomainMovieSearchMapper
import com.elhady.movies.core.data.repository.mappers.domain.search.DomainTvShowSearchMapper
import com.elhady.movies.core.data.repository.mappers.domain.tv.DomainAiringTodayTVMapper
import com.elhady.movies.core.data.repository.mappers.domain.tv.DomainAiringTodayTvShowsMapper
import com.elhady.movies.core.data.repository.mappers.domain.tv.DomainMyRatedTvShowDetailsMapper
import com.elhady.movies.core.data.repository.mappers.domain.tv.DomainTVMapper
import com.elhady.movies.core.data.repository.showmore.PopularMoviesShowMorePagingSource
import com.elhady.movies.core.data.repository.showmore.TopRatedShowMorePagingSource
import com.elhady.movies.core.data.repository.showmore.TrendingShowMorePagingSource
import com.elhady.movies.core.data.repository.myrated.RatedMoviesPagingSource
import com.elhady.movies.core.data.repository.myrated.RatedTvShowPagingSource
import com.elhady.movies.core.data.repository.tvshows.AiringTodayTVShowsPagingSource
import com.elhady.movies.core.data.repository.tvshows.OnTheAirTVShowsPagingSource
import com.elhady.movies.core.data.repository.tvshows.PopularTVShowsPagingSource
import com.elhady.movies.core.data.repository.tvshows.TopRatedTVShowsPagingSource
import com.elhady.movies.core.domain.entities.EpisodeDetailsEntity
import com.elhady.movies.core.domain.entities.GenreEntity
import com.elhady.movies.core.domain.entities.MovieEntity
import com.elhady.movies.core.domain.entities.PeopleDetailsEntity
import com.elhady.movies.core.domain.entities.PeopleEntity
import com.elhady.movies.core.domain.entities.RatingEpisodeDetailsStatusEntity
import com.elhady.movies.core.domain.entities.SeasonEntity
import com.elhady.movies.core.domain.entities.StatusEntity
import com.elhady.movies.core.domain.entities.tvdetails.TvDetailsInfoEntity
import com.elhady.movies.core.domain.entities.TvEntity
import com.elhady.movies.core.domain.entities.TvShowEntity
import com.elhady.movies.core.domain.entities.UserListEntity
import com.elhady.movies.core.domain.entities.YoutubeVideoDetailsEntity
import com.elhady.movies.core.domain.entities.moviedetails.MovieDetailsEntity
import com.elhady.movies.core.domain.entities.ReviewEntity
import com.elhady.movies.core.domain.entities.TVShowsEntity
import com.elhady.movies.core.domain.entities.moviedetails.ReviewResponseEntity
import com.elhady.movies.core.domain.entities.mylist.ListCreatedEntity
import com.elhady.movies.core.domain.entities.myrated.MyRatedMovieEntity
import com.elhady.movies.core.domain.entities.myrated.MyRatedTvShowEntity
import com.elhady.movies.core.domain.entities.seasondetails.SeasonDetailsEntity
import com.elhady.movies.core.domain.usecase.repository.ApiThrowable
import com.elhady.movies.core.domain.usecase.repository.MovieRepository
import java.util.Locale
import java.util.Random
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieService,
    private val movieDao: MovieDao,
    private val ratedMoviesPagingSource: RatedMoviesPagingSource,
    private val ratedTvShowPagingSource: RatedTvShowPagingSource,
    private val airingTodayTvShowsPagingSource: AiringTodayTVShowsPagingSource,
    private val topRatedTvShowsPagingSource: TopRatedTVShowsPagingSource,
    private val onTheAirTVShowsPagingSource: OnTheAirTVShowsPagingSource,
    private val popularTVShowsPagingSource: PopularTVShowsPagingSource,
    private val preferenceStorage: PreferenceStorage,
    private val localGenresMovieMapper: LocalGenresMovieMapper,
    private val localGenresTvMapper: LocalGenresTvMapper,
    private val localPopularMovieMapper: LocalPopularMovieMapper,
    private val localPopularPeopleMapper: LocalPopularPeopleMapper,
    private val localNowPlayingMovieMapper: LocalNowPlayingMovieMapper,
    private val localTopRatedMovieMapper: LocalTopRatedMovieMapper,
    private val localTrendingMoviesMapper: LocalTrendingMoviesMapper,
    private val localUpcomingMovieMapper: LocalUpcomingMovieMapper,
    private val domainPopularMovieMapper: DomainPopularMovieMapper,
    private val domainNowPlayingMovieMapper: DomainNowPlayingMovieMapper,
    private val domainTopRatedMovieMapper: DomainTopRatedMovieMapper,
    private val domainUpcomingMovieMapper: DomainUpcomingMovieMapper,
    private val domainTrendingMovieMapper: DomainTrendingMoviesMapper,
    private val domainPeopleMapper: DomainPeopleMapper,
    private val domainGenreMapper: DomainGenreMapper,
    private val domainMovieDetailsMapper: DomainMovieDetailsMapper,
    private val domainStatusMapper: DomainStatusMapper,
    private val domainRatingEpisodeMapper: DomainRatingEpisodeMapper,
    private val domainCastMapper: DomainCastMapper,
    private val domainEpisodeDetailsMapper: DomainEpisodeDetailsMapper,
    private val domainGenreTvMapper: DomainGenreTvMapper,
    private val domainSeasonDetailsMapper: DomainSeasonDetailsMapper,
    private val domainPeopleRemoteMapper: DomainPeopleRemoteMapper,
    private val popularMovieMapperShowMore: PopularMoviesShowMorePagingSource,
    private val topRatedShowMorePagingSource: TopRatedShowMorePagingSource,
    private val trendingShowMorePagingSource: TrendingShowMorePagingSource,
    private val domainTvDetailsMapper: DomainTvDetailsMapper,
    private val domainYoutubeDetailsMapper: DomainYoutubeDetailsMapper,
    private val domainTvDetailsCreditMapper: DomainTvDetailsCreditMapper,
    private val domainTvDetailsReviewMapper: DomainTvDetailsReviewMapper,
    private val domainTvShowMapper: DomainTvShowMapper,
    private val domainTVMapper: DomainTVMapper,
    private val domainTvDetailsSeasonMapper: DomainTvDetailsSeasonMapper,
    private val domainMovieMapper: DomainMovieMapper,
    private val domainMovieSearchMapper: DomainMovieSearchMapper,
    private val domainTvShowSearchMapper: DomainTvShowSearchMapper,
    private val domainTvMapper: DomainTvMapper,
    private val domainReviewsMapper: DomainReviewsMapper,
    private val domainUserListsMapper: DomainUserListsMapper,
    private val localTvShowMapper: LocalTvShowMapper,
    private val tvShowDao: TvShowDao,
    private val domainAiringTodayTvShowsMapper: DomainAiringTodayTvShowsMapper,
    private val localAiringTodayTvShowMapper: LocalAiringTodayTvShowMapper,
    private val domainAiringTodayTVMapper: DomainAiringTodayTVMapper,
    private val random: Random,
    private val domainPeopleDetailsMapper: DomainPeopleDetailsMapper,
    private val domainMoviesByPeopleMapper: DomainMoviesByPeopleMapper,
    private val tvShowsByPeopleMapper: DomainTvShowsByPeopleMapper,
    private val domainMyRatedMoviesDetailsMapper: DomainMyRatedMoviesDetailsMapper,
    private val domainMyRatedTvShowDetailsMapper: DomainMyRatedTvShowDetailsMapper
) : BaseRepository(), MovieRepository {

    /// region showMore
    override suspend fun getPopularMoviesPaging(): Pager<Int, MovieEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { popularMovieMapperShowMore }
        )
    }

    override suspend fun getTopRateMoviesPaging(): Pager<Int, MovieEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { topRatedShowMorePagingSource }
        )
    }

    override suspend fun getTrendingMoviesPaging(): Pager<Int, MovieEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { trendingShowMorePagingSource }
        )
    }
    /// endregion

    /// region movies
    override suspend fun getPopularMoviesFromDatabase(): List<MovieEntity> {
        return domainPopularMovieMapper.map(movieDao.getPopularMovies())
    }

    override suspend fun getPopularMoviesFromRemote(): List<MovieEntity> {
        val page = random.nextInt(500) + 1
        val moviesRemoteDTOs =
            wrapApiCall { movieService.getPopularMovies(page = page) }.results?.filterNotNull()
                ?: emptyList()
        val genres = getGenresMovies()
        return domainMovieMapper.map(moviesRemoteDTOs, genres)
    }

    override suspend fun refreshPopularMovies() {
        refreshWrapper(
            apiCall = { movieService.getPopularMovies(random.nextInt(20) + 1) },
            localMapper = localPopularMovieMapper::map,
            databaseSaver = movieDao::insertPopularMovies,
            clearOldLocalData = movieDao::clearAllPopularMovies
        )
    }

    override suspend fun getNowPlayingMovies(): List<MovieEntity> {
        return domainNowPlayingMovieMapper.map(movieDao.getNowPlayingMovies())
    }

    override suspend fun refreshNowPlayingMovies() {
        refreshWrapper(
            { movieService.getNowPlayingMovies(random.nextInt(20) + 1) },
            localNowPlayingMovieMapper::map,
            movieDao::insertNowPlayingMovies,
            clearOldLocalData = movieDao::clearAllNowPlayingMovies
        )
    }

    override suspend fun getTopRatedMovies(): List<MovieEntity> {
        return domainTopRatedMovieMapper.map(movieDao.getTopRatedMovies())
    }

    override suspend fun refreshTopRatedMovies() {
        refreshWrapper(
            { movieService.getTopRatedMovies(random.nextInt(20) + 1) },
            localTopRatedMovieMapper::map,
            movieDao::insertTopRatedMovies,
            clearOldLocalData = movieDao::clearAllTopRatedMovies
        )
    }

    override suspend fun getUpcomingMoviesFromDatabase(): List<MovieEntity> {
        return domainUpcomingMovieMapper.map(movieDao.getUpcomingMovies())
    }

    override suspend fun refreshUpcomingMovies() {
        val genres = getGenresMovies()
        refreshWrapper(
            apiCall = movieService::getUpcomingMovies,
            localMapper = { localUpcomingMovieMapper.map(it, genres) },
            databaseSaver = movieDao::insertUpcomingMovies,
            clearOldLocalData = movieDao::clearAllUpcomingMovies
        )
    }

    override suspend fun getTrendingMovies(): List<MovieEntity> {
        return domainTrendingMovieMapper.map(movieDao.getTrendingMovies())
    }

    override suspend fun refreshTrendingMovies() {
        val genres = getGenresMovies()
        refreshWrapper(
            apiCall = movieService::getTrendingMovies,
            localMapper = { localTrendingMoviesMapper.map(it, genres)},
            databaseSaver = movieDao::insertTrendingMovies,
            clearOldLocalData = movieDao::clearAllTrendingMovies
        )
    }

    override suspend fun getPopularPeopleFromDatabase(): List<PeopleEntity> {
        return domainPeopleMapper.map(movieDao.getPopularPeople())
    }

    override suspend fun getPopularPeopleFromRemote(): List<PeopleEntity> {
        val page = random.nextInt(20) + 1
        val call =
            wrapApiCall { movieService.getPopularPeople(page = page) }.results?.filterNotNull()
                ?: emptyList()
        return domainPeopleRemoteMapper.map(call)
    }

    override suspend fun refreshPopularPeople() {
        refreshWrapper(
            { movieService.getPopularPeople(random.nextInt(20) + 1) },
            localPopularPeopleMapper::map,
            movieDao::insertPopularPeople,
            clearOldLocalData = movieDao::clearAllPopularPeople
        )
    }
    /// endregion

    /// region search history
    override suspend fun getSearchHistory(keyword: String): List<String> {
        return movieDao.getSearchHistory("%${keyword}%").map { it.keyword }

    }

    override suspend fun searchHistory(): List<String> {
        return movieDao.getSearchHistory().map { it.keyword }
    }

    override suspend fun insertSearchHistory(keyword: String) {
        return movieDao.insertSearchHistory(SearchHistoryLocalDto(keyword))
    }

    override suspend fun clearAllSearchHistory() {
        return movieDao.clearAllSearchHistory()
    }

    override suspend fun deleteSearchHistory(keyword: String) {
        movieDao.deleteSearchHistory(keyword)
    }
    /// endregion

    ///region search
    override suspend fun searchForMovies(keyword: String): List<MovieEntity> {
        val movieRemoteDto =
            wrapApiCall { movieService.searchForMovies(keyword) }.results?.filterNotNull()
                ?: emptyList()
        val genresEntities = getGenresMovies()
        return domainMovieSearchMapper.map(movieRemoteDto, genresEntities)
    }

    override suspend fun searchForTv(keyword: String): List<TvEntity> {
        val tvRemoteDto = wrapApiCall { movieService.searchForTv(keyword) }.results?.filterNotNull()
            ?: emptyList()
        val genresTvEntities = getGenresTvs()
        return domainTvShowSearchMapper.map(tvRemoteDto, genresTvEntities)
    }

    override suspend fun searchForPeople(keyword: String): List<PeopleEntity> {
        return domainPeopleRemoteMapper.map(
            wrapApiCall { movieService.searchForPeople(keyword) }.results?.filterNotNull()
                ?: emptyList()
        )
    }

    private fun filterGenres(
        genresIds: List<Int>,
        genresEntities: List<GenreEntity>
    ): List<GenreEntity> {
        return genresEntities.filter { it.genreID in genresIds }
    }
    //endregion

    /// region genres
    override suspend fun getGenresMovies(): List<GenreEntity> {
        return domainGenreMapper.map(movieDao.getGenresMovies())
    }

    override suspend fun refreshGenres() {
        wrapApiCall { movieService.getListOfGenresForMovies() }.results
            ?.let { remoteGenres ->
                movieDao.insertGenresMovies(localGenresMovieMapper.map(remoteGenres))
            }
    }


    override suspend fun getGenresTvs(): List<GenreEntity> {
        return domainGenreTvMapper.map(movieDao.getGenresTvs())
    }

    override suspend fun refreshGenresTv() {
        try {
            wrapApiCall { movieService.getListOfGenresForTvs() }.results
                ?.let { remoteGenres ->
                    movieDao.insertGenresTvs(localGenresTvMapper.map(remoteGenres))
                }

        } catch (_: Throwable) {
        }
    }
    /// endregion

    /// region refresh time
    override suspend fun getLastRefreshTime(): Long? {
        return preferenceStorage.lastRefreshTime
    }

    override suspend fun setLastRefreshTime(time: Long) {
        preferenceStorage.setLastRefreshTime(time)
    }

    override suspend fun refreshAll() {
        refreshGenres()
        refreshGenresTv()
        refreshPopularMovies()
        refreshPopularPeople()
        refreshNowPlayingMovies()
        refreshTopRatedMovies()
        refreshTrendingMovies()
        refreshUpcomingMovies()
    }

    /// endregion

    /// region movies details
    override suspend fun getMoviesDetails(movieId: Int): MovieDetailsEntity {
        return domainMovieDetailsMapper.map(wrapApiCall { movieService.getMovieDetails(movieId) })
    }
    /// endregion

    /// region season details
    override suspend fun getSeasonDetails(seriesId: Int, seasonId: Int): SeasonDetailsEntity {
        val result = wrapApiCall { movieService.getSeasonDetails(seriesId, seasonId) }
        return domainSeasonDetailsMapper.map(result)
    }

    /// endregion

    ///region tv

    // region Airing Today
    override suspend fun refreshAiringTodayTvShows() {
        refreshWrapper(
            apiCall = { movieService.getAiringTodayTVShows(random.nextInt(20) + 1) },
            localMapper = localAiringTodayTvShowMapper::map,
            databaseSaver = tvShowDao::insertAiringTodayTvShow,
            clearOldLocalData = tvShowDao::clearAllAiringTodayTvShow
        )
    }

    override suspend fun getAiringTodayTvShowsFromDatabase(): List<TVShowsEntity> {
        Log.d("dao TVREPOSITORY" , "Airing Dao : ${tvShowDao.getAllTvShow().size}")
        return domainAiringTodayTVMapper.map(tvShowDao.getAllAiringTodayTvShow())
    }

    override suspend fun getAiringTodayTVShowsFromRemote(): List<TVShowsEntity> {
        val page = random.nextInt(500) + 1
        val airingTodayRemoteDTOs =
            wrapApiCall { movieService.getAiringTodayTVShows(page = page) }.results?.filterNotNull() ?: emptyList()
        Log.d("remote TVREPOSITORY" , "${airingTodayRemoteDTOs.size}")
        return domainAiringTodayTvShowsMapper.map(airingTodayRemoteDTOs)
    }

    // endregion

    override suspend fun getTvShowsFromDatabase(): List<TVShowsEntity> {
        return domainTVMapper.map(tvShowDao.getAllTvShow())
    }

    override suspend fun refreshTvShows() {
        try {
            val items = mutableListOf<TvShowsLocalDto>()
            movieService.getAiringTodayTVShows().body()?.results?.first()
                ?.let {
                    items.add(localTvShowMapper.map(it))
                }
            movieService.getTopRatedTVShows().body()?.results?.first()
                ?.let {
                    items.add(localTvShowMapper.map(it))
                }
            movieService.getPopularTVShows().body()?.results?.first()
                ?.let {
                    items.add(localTvShowMapper.map(it))
                }
            movieService.getOnTheAirTVShows().body()?.results?.first()
                ?.let {
                    items.add(localTvShowMapper.map(it))
                }
            tvShowDao.clearAllTvShow()
            tvShowDao.insertTvShow(items)

        } catch (throwable: Throwable) {
            throw ApiThrowable(throwable.message)
        }
    }



    override suspend fun getAiringTodayTVShowsPager(): Pager<Int, TVShowsEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { airingTodayTvShowsPagingSource }
        )
    }

    override suspend fun getTopRatedTVShowsPager(): Pager<Int, TVShowsEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { topRatedTvShowsPagingSource }
        )
    }

    override suspend fun getPopularTVShowsPager(): Pager<Int, TVShowsEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { popularTVShowsPagingSource }
        )
    }

    override suspend fun getOnTheAirTVShowsPager(): Pager<Int, TVShowsEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { onTheAirTVShowsPagingSource }
        )
    }

    override suspend fun getTvDetailsInfo(tvShowID: Int): TvDetailsInfoEntity {
        return domainTvDetailsMapper.map(wrapApiCall { movieService.getTvDetails(tvShowID) })
    }

    override suspend fun getTvDetailsCredit(tvShowID: Int): List<PeopleEntity> {
        return domainTvDetailsCreditMapper.map(
            wrapApiCall {
                movieService.getTvDetailsCredit(tvShowID)
            })
    }

    override suspend fun rateTvShow(rate: Double, tvShowID: Int): StatusEntity {
        val newRate = RateRequest(value = rate)
        return domainStatusMapper.map(wrapApiCall {
            movieService.rateTvShow(
                newRate,
                tvShowID
            )
        })
    }

    override suspend fun getRateTvShow(): List<MyRatedTvShowEntity> {
        return domainMyRatedTvShowDetailsMapper.map(
            wrapApiCall { movieService.getRatedTv() }.results?.filterNotNull() ?: emptyList()
        )
    }


    //region my list
    override suspend fun getFavoriteMovies(): List<MovieEntity> {
        val genresEntities = getGenresMovies()
        val result = wrapApiCall { movieService.getFavoriteMovies() }.results
        return result?.map { item ->
            domainMovieMapper.map(
                input = item!!,
                genres = filterGenres(
                    item.genreIds?.filterNotNull() ?: emptyList(),
                    genresEntities
                ),
                mediaType = "movie",
            )
        } ?: emptyList()
    }

    override suspend fun getFavoriteTv(): List<MovieEntity> {
        val genresEntities = getGenresMovies()
        val result = wrapApiCall { movieService.getFavoriteTv() }.results
        return result?.map { item ->
            domainTvMapper.map(
                input = item!!,
                genres = filterGenres(
                    item.genreIds?.filterNotNull() ?: emptyList(),
                    genresEntities
                ),
                mediaType = "tv",
            )
        } ?: emptyList()
    }

    override suspend fun getWatchlistMovies(): List<MovieEntity> {
        val genresEntities = getGenresMovies()
        val result = wrapApiCall { movieService.getWatchlist() }.results
        return result?.map { item ->
            domainMovieMapper.map(
                input = item!!,
                genres = filterGenres(
                    item.genreIds?.filterNotNull() ?: emptyList(),
                    genresEntities
                ),
                mediaType = "movie",
            )
        } ?: emptyList()
    }

    override suspend fun getWatchlistTv(): List<MovieEntity> {
        val genresEntities = getGenresMovies()
        val result = wrapApiCall { movieService.getWatchlistTv() }.results
        return result?.map { item ->
            domainTvMapper.map(
                input = item!!,
                genres = filterGenres(
                    item.genreIds?.filterNotNull() ?: emptyList(),
                    genresEntities
                ),
                mediaType = "tv"
            )
        } ?: emptyList()
    }

    override suspend fun setMovieRate(movieId: Int, rate: Float): StatusEntity {
        return domainStatusMapper.map(wrapApiCall {
            movieService.setMovieRate(ratingRequest = RatingRequest(rate), movieId = movieId)
        })
    }

    override suspend fun getMovieRate(): List<MyRatedMovieEntity> {
        return domainMyRatedMoviesDetailsMapper.map(
            wrapApiCall { movieService.getRatedMovies() }.results?.filterNotNull() ?: emptyList()
        )
    }

    override suspend fun getMovieReviews(movieId: Int, page: Int): ReviewResponseEntity {
        return domainReviewsMapper.map(wrapApiCall { movieService.getMovieReviews(movieId, page) })
    }

    override suspend fun addWatchlist(
        mediaId: Int,
        mediaType: String,
        isWatchList: Boolean
    ): StatusEntity {
        val watchlistRequest = WatchlistRequest(
            mediaId = mediaId,
            mediaType = mediaType,
            watchlist = isWatchList
        )
        val call = wrapApiCall { movieService.addWatchlist(watchlistRequest) }
        return domainStatusMapper.map(call)
    }

    override suspend fun addFavouriteList(
        mediaId: Int,
        mediaType: String,
        isFavourite: Boolean
    ): StatusEntity {
        val favoriteRequest = FavoriteRequest(
            mediaId = mediaId,
            mediaType = mediaType,
            favorite = isFavourite
        )
        val call = wrapApiCall { movieService.addFavorite(favoriteRequest) }
        return domainStatusMapper.map(call)
    }

    override suspend fun addList(name: String): Boolean {
        return movieService.addList(ListRequest(name = name)).isSuccessful
    }

    override suspend fun getTvShowReviews(tvShowID: Int): List<ReviewEntity> {
        val call = wrapApiCall { movieService.getTvShowReviews(tvShowID) }.results?.filterNotNull()
            ?: emptyList()
        return domainTvDetailsReviewMapper.map(call)

    }

    override suspend fun getTvShowRecommendations(tvShowID: Int): List<TvShowEntity> {
        val call =
            wrapApiCall { movieService.getTvShowRecommendations(tvShowID) }.results?.filterNotNull()
                ?: emptyList()
        return domainTvShowMapper.map(call)
    }

    override suspend fun getTvDetailsSeasons(tvShowID: Int): List<SeasonEntity> {
        return domainTvDetailsSeasonMapper.map(wrapApiCall { movieService.getTvDetails(tvShowID) })
    }


    /// endregion

    /// region user list
    override suspend fun getUserLists(): List<UserListEntity> {
        val call =
            wrapApiCall { movieService.getUserLists() }.results?.filterNotNull() ?: emptyList()
        return domainUserListsMapper.map(call)
    }

    override suspend fun postUserLists(listId: Int, mediaId: Int): StatusEntity {
        val addMediaRequest = AddMediaToListRequest(mediaId)
        val call = wrapApiCall { movieService.postUserMedia(listId, addMediaRequest) }
        return domainStatusMapper.map(call)
    }

    override suspend fun createUserList(listName: String): StatusEntity {
        val newList = CreateUserListRequest(listName)
        val call = wrapApiCall { movieService.createUserList(newList) }
        return domainStatusMapper.map(call)
    }
    /// endregion

    override suspend fun getDetailsList(listId: Int): List<MovieEntity> {
        val genresEntities = getGenresMovies()
        val result = wrapApiCall { movieService.getDetailsList(listId) }.items
        return result?.map { item ->
            domainMovieMapper.map(
                input = item,
                genres = filterGenres(
                    item.genreIds?.filterNotNull() ?: emptyList(),
                    genresEntities
                )
            )
        } ?: emptyList()
    }

    override suspend fun deleteMovieDetailsList(listId: Int, mediaId: Int): StatusEntity {
        val call = wrapApiCall {
            movieService.deleteMovieDetailsList(
                listId = listId,
                DeleteMovieRequest(mediaId = mediaId)
            )
        }
        return domainStatusMapper.map(call)

    }

    override suspend fun deleteList(listId: Int): StatusEntity {
        return domainStatusMapper.map(wrapApiCall { movieService.deleteList(listId = listId) })
    }


    override suspend fun getListCreated(): List<ListCreatedEntity> {
        return wrapApiCall { movieService.getLists() }.results
            ?.filterNotNull()?.let { lists ->
                lists.map { input ->
                    ListCreatedEntity(
                        id = input.id,
                        itemCount = input.itemCount,
                        listType = input.listType,
                        name = input.name,
                        posterPath = getDetailsList(input.id ?: 0)
                            .map { items ->
                                items.imageUrl
                            }
                    )
                }
            } ?: emptyList()
    }

    //endregion

    /// region episode

    override suspend fun getCastForEpisode(
        id: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): List<PeopleEntity> {
        val dataDto = wrapApiCall { movieService.getEpisodeCast(id, seasonNumber, episodeNumber) }
        return domainCastMapper.map(dataDto)

    }

    override suspend fun getEpisodeDetails(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): EpisodeDetailsEntity {
        return domainEpisodeDetailsMapper.map(wrapApiCall {
            movieService.getEpisodeDetails(
                seriesId,
                seasonNumber,
                episodeNumber
            )
        })
    }

    override suspend fun setRatingForEpisode(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int,
        value: Float
    ): RatingEpisodeDetailsStatusEntity {
        val rateRequest = RatingEpisodeDetailsRequest(value)
        return domainRatingEpisodeMapper.map(wrapApiCall {
            movieService.postEpisodeRating(
                rateRequest,
                seriesId,
                seasonNumber,
                episodeNumber
            )
        })
    }

    /// endregion

    /// region trailer
    override suspend fun getTrailerVideoForTvShow(tvShowID: Int): YoutubeVideoDetailsEntity {
        val call =
            wrapApiCall { movieService.getTrailerVideoForTvShow(tvShowID) }.results?.first()
                ?: YoutubeVideoDetailsRemoteDto()
        return domainYoutubeDetailsMapper.map(call)
    }

    override suspend fun getTrailerVideoForMovie(movieID: Int): YoutubeVideoDetailsEntity {
        val call =
            wrapApiCall { movieService.getTrailerVideoForMovie(movieID) }.results?.first()
                ?: YoutubeVideoDetailsRemoteDto()
        return domainYoutubeDetailsMapper.map(call)
    }

    override suspend fun getVideoEpisodeDetails(
        seriesId: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ): YoutubeVideoDetailsEntity {
        val response =
            wrapApiCall { movieService.getEpisodeVideos(seriesId, seasonNumber, episodeNumber) }
                .results?.first() ?: YoutubeVideoDetailsRemoteDto()

        return domainYoutubeDetailsMapper.map(response)
    }

    /// endregion

    override fun isLoginOrNot(): Boolean {
        return !preferenceStorage.sessionId.isNullOrBlank()
    }


    /// region myRated
    override suspend fun getRatedMovies(): Pager<Int, MyRatedMovieEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { ratedMoviesPagingSource }
        )
    }

    override suspend fun getRatedTvShows(): Pager<Int, MyRatedTvShowEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { ratedTvShowPagingSource }
        )
    }
    /// endregion

    // region people details
    override suspend fun getPersonDetails(personId: Int): PeopleDetailsEntity {
        return domainPeopleDetailsMapper.map(wrapApiCall { movieService.getPerson(personId) })
    }

    override suspend fun getMoviesByPerson(personId: Int): List<MovieEntity> {
        return domainMoviesByPeopleMapper.map(wrapApiCall { movieService.getMoviesByPerson(personId) }.cast!!)
    }

    override suspend fun getTvShowsByPerson(personId: Int): List<TvShowEntity> {
        return tvShowsByPeopleMapper.map(wrapApiCall {
            movieService.getTvShowsByPerson(
                personId
            )
        }.cast!!)
    }
    // endregion
}