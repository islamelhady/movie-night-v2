package com.elhady.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.elhady.entities.GenreEntity
import com.elhady.entities.MovieEntity
import com.elhady.entities.PopularMovieEntity
import com.elhady.local.AppConfiguration
import com.elhady.local.database.dao.MovieDao
import com.elhady.local.database.dto.SearchHistoryLocalDto
import com.elhady.local.database.dto.WatchHistoryLocalDto
import com.elhady.local.database.dto.movies.AdventureMovieLocalDto
import com.elhady.local.database.dto.movies.MysteryMovieLocalDto
import com.elhady.local.database.dto.movies.NowPlayingMovieLocalDto
import com.elhady.local.database.dto.movies.TrendingMovieLocalDto
import com.elhady.repository.mappers.cash.movies.LocalAdventureMoviesMapper
import com.elhady.repository.mappers.cash.movies.LocalMysteryMoviesMapper
import com.elhady.repository.mappers.cash.movies.LocalNowPlayingMovieMapper
import com.elhady.repository.mappers.domain.movie.DomainPopularMovieMapper
import com.elhady.repository.mappers.cash.movies.LocalTopRatedMovieMapper
import com.elhady.repository.mappers.cash.movies.LocalTrendingMovieMapper
import com.elhady.remote.response.AddListResponse
import com.elhady.remote.response.AddMovieDto
import com.elhady.remote.response.CreatedListDto
import com.elhady.remote.response.CreditsDto
import com.elhady.remote.response.FavListDto
import com.elhady.remote.response.RatedMovieDto
import com.elhady.remote.response.StatusResponse
import com.elhady.remote.response.SavedListDto
import com.elhady.remote.response.StatusResponseDto
import com.elhady.remote.response.dto.MovieRemoteDto
import com.elhady.remote.response.movie.MovieDetailsDto
import com.elhady.remote.response.review.ReviewDto
import com.elhady.remote.response.video.VideoDto
import com.elhady.remote.serviece.MovieService
import com.elhady.repository.mappers.cash.LocalGenresMovieMapper
import com.elhady.repository.mappers.cash.LocalPopularMovieMapper
import com.elhady.repository.mappers.cash.LocalUpcomingMovieMapper
import com.elhady.repository.mappers.domain.DomainGenreMapper
import com.elhady.repository.mappers.domain.movie.DomainMysteryMoviesMapper
import com.elhady.repository.mappers.domain.movie.DomainNowPlayingMovieMapper
import com.elhady.repository.mappers.domain.movie.DomainTopRatedMovieMapper
import com.elhady.repository.mappers.domain.movie.DomainUpcomingMovieMapper
import com.elhady.repository.mediaDataSource.movies.MovieDataSourceContainer
import com.elhady.repository.searchDataSource.MovieSearchDataSource
import com.elhady.repository.showMore.PopularMoviesShowMorePagingSource
import com.elhady.usecase.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date
import java.util.Random
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
    private val movieService: MovieService,
    private val random: Random,
    private val domainPopularMovieMapper: DomainPopularMovieMapper,
    private val localPopularMovieMapper: LocalPopularMovieMapper,
    private val localUpcomingMovieMapper: LocalUpcomingMovieMapper,
    private val localGenresMovieMapper: LocalGenresMovieMapper,
    private val localTopRatedMovieMapper: LocalTopRatedMovieMapper,
    private val domainTopRatedMovieMapper: DomainTopRatedMovieMapper,
    private val localAdventureMoviesMapper: LocalAdventureMoviesMapper,
    private val localNowPlayingMovieMapper: LocalNowPlayingMovieMapper,
    private val domainNowPlayingMovieMapper: DomainNowPlayingMovieMapper,
    private val localTrendingMovieMapper: LocalTrendingMovieMapper,
    private val localMysteryMoviesMapper: LocalMysteryMoviesMapper,
    private val domainMysteryMoviesMapper: DomainMysteryMoviesMapper,
    private val domainGenreMapper: DomainGenreMapper,
    private val domainUpcomingMovieMapper: DomainUpcomingMovieMapper,
    private val popularMovieMapperShowMore: PopularMoviesShowMorePagingSource,
    private val movieDao: MovieDao,
    private val appConfiguration: AppConfiguration,
//    private val statusResponseMapper: StatusResponseMapper,
    private val movieDataSourceContainer: MovieDataSourceContainer,
    private val movieSearchDataSource: MovieSearchDataSource
) : BaseRepository(), MovieRepository {

    /**
     *  Popular Movies
     */
    override suspend fun getPopularMoviesFromRemote(): List<PopularMovieEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getPopularMoviesFromDatabase(): List<PopularMovieEntity> {
        return domainPopularMovieMapper.map(movieDao.getPopularMovies())
    }

    override suspend fun refreshPopularMovies() {
        val genre = movieService.getListOfGenresForMovies().body()?.results ?: emptyList()
        refreshWrapper(
            { movieService.getPopularMovies(page = random.nextInt(20) + 1) },
            { localPopularMovieMapper.map(it, genreList = genre) },
            movieDao::deletePopularMovie,
            movieDao::insertPopularMovie
        )
    }

    /**
     *  Genre Movies List
     */
    override suspend fun getGenreMovies(): List<GenreEntity> {
        return domainGenreMapper.map(movieDao.getGenresMovies())
    }

    override suspend fun refreshGenres() {
        try {
            wrapApiCall { movieService.getListOfGenresForMovies() }
                .results?.let { remoteGenres ->
                    movieDao.insertGenresMovies(localGenresMovieMapper.map(remoteGenres))
                }
        } catch (_: Throwable) {
        }
    }

    /**
     *  Movies By Genre
     */
    override fun getMoviesByGenre(genreId: Int): Pager<Int, MovieRemoteDto> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                val movieDataSource = movieDataSourceContainer.moviesByGenreDataSource
                movieDataSource.setGenre(genreId)
                movieDataSource
            })
    }

    /**
     *  Upcoming Movies
     */

    override suspend fun getUpcomingMoviesFromDatabase(): List<MovieEntity> {
        return domainUpcomingMovieMapper.map(movieDao.getUpcomingMovies())
    }

    override suspend fun refreshUpcomingMovies() {
        refreshWrapper(
            { movieService.getUpcomingMovies(page = random.nextInt(20) + 1) },
            { localUpcomingMovieMapper.map(it) },
            movieDao::deleteUpcomingMovies,
            movieDao::insertUpcomingMovie
        )
    }

    /**
     *  All Popular Movies
     */
    override fun getUpcomingMoviesPaging(): Pager<Int, MovieEntity> {
        return Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = { popularMovieMapperShowMore })
    }

    /**
     *  Top Rated Movies
     */
    override suspend fun getTopRatedMoviesFromDatabase(): List<MovieEntity> {
        return domainTopRatedMovieMapper.map(movieDao.getTopRatedMovies())
    }

    override suspend fun refreshTopRatedMovies{
        refreshWrapper(
            { movieService.getTopRatedMovies(page = random.nextInt(20) + 1) },
            { localTopRatedMovieMapper.map(it) },
            movieDao::deleteTopRatedMovies,
            movieDao::insertTopRatedMovies
        )
    }

    /**
     *  All Top Rated Movies
     */
    override fun getAllTopRatedMovies(): Pager<Int, MovieRemoteDto> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { movieDataSourceContainer.topRatedMovieDataSource })
    }

    /**
     *  Now Playing Movies
     */
    override suspend fun getNowPlayingMoviesFromDatabase(): List<MovieEntity> {
        return domainNowPlayingMovieMapper.map(movieDao.getNowPlayingMovies())
    }

    override suspend fun refreshNowPlayingMovies() {
        refreshWrapper(
            { movieService.getNowPlayingMovies(page = random.nextInt(20) + 1) },
            { localNowPlayingMovieMapper.map(it) },
            movieDao::deleteNowPlayingMovies,
            movieDao::insertNowPlayingMovies
        )
    }

    /**
     *  All Trending Movies
     */

    override fun getAllNowPlayingMovies(): Pager<Int, MovieRemoteDto> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { movieDataSourceContainer.nowPlayingMovieDataSource })
    }

    /**
     *  Trending Movies
     */
    override suspend fun getTrendingMovie(): List<TrendingMovieLocalDto> {
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
    override fun getAllTrendingMovies(): Pager<Int, MovieRemoteDto> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { movieDataSourceContainer.trendingMovieDataSource })
    }

    /**
     *  Mystery Movies
     */
    override suspend fun getMysteryMoviesFromDatabase(): List<MovieEntity> {
        return domainMysteryMoviesMapper.map(movieDao.getMysteryMovies())
    }

    override suspend fun refreshMysteryMovies() {
        refreshWrapper(
            { movieService.getMoviesListByGenre(page = random.nextInt(20)+1, genreID = Constant.MYSTERY_ID) },
            { localMysteryMoviesMapper.map(it) },
            movieDao::deleteMysteryMovies,
            movieDao::insertMysteryMovies
        )
    }
    /**
     *  All Mystery Movies
     */

    override fun getAllMysteryMovies(): Pager<Int, MovieRemoteDto> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { movieDataSourceContainer.mysteryMovieDataSource })
    }

    /**
     *  Adventure Movies
     */
    override suspend fun getAdventureMovies(): List<AdventureMovieLocalDto> {
        refreshOneTimePerDay(
            appConfiguration.getRequestDate(Constant.ADVENTURE_MOVIE_REQUEST_DATE_KEY),
            ::refreshAdventureMovies
        )
        return movieDao.getAdventureMovies()
    }

    suspend fun refreshAdventureMovies(currentDate: Date) {
        wrap(
            { movieService.getMoviesListByGenre(genreID = Constant.ADVENTURE_ID) },
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
    override fun getAllAdventureMovies(): Pager<Int, MovieRemoteDto> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { movieDataSourceContainer.adventureMovieDataSource })
    }

    /**
     * Movie Details
     * * Details movies
     * * Cast
     * * Similar movies
     * * Review
     */
    override suspend fun getDetailsMovies(movieId: Int): MovieDetailsDto? {
        return movieService.getDetailsMovies(movieId = movieId).body()
    }

    override suspend fun getMovieCast(movieId: Int): CreditsDto? {
        return movieService.getMovieCast(movieId = movieId).body()
    }

    override suspend fun getSimilarMovies(movieId: Int): List<MovieRemoteDto>? {
        return movieService.getSimilarMovie(movieId = movieId).body()?.results
    }

    override suspend fun getMovieReview(movieId: Int): List<ReviewDto>? {
        return movieService.getMovieReview(movieId).body()?.results
    }

    /**
     *  All Movies
     */
    override fun getAllMovies(): Pager<Int, MovieRemoteDto> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { movieDataSourceContainer.moviesDataSource })
    }

    /**
     * Serach
     * * movies
     * * history
     */
    override suspend fun searchForMoviesPager(query: String): Pager<Int, MovieRemoteDto> {
        val dataSource = movieSearchDataSource
        dataSource.setSearch(query = query)
        return Pager(config = pagingConfig, pagingSourceFactory = { dataSource })
    }

    override suspend fun insertSearchHistory(searchHistory: String) {
        return movieDao.insertSearchHistory(SearchHistoryLocalDto(keyword = searchHistory))
    }

    override suspend fun deleteSearchHistory(keyword: String) {
        return movieDao.deleteSearch(keyword)
    }

    override suspend fun getSearchHistory(): List<SearchHistoryLocalDto> {
        return movieDao.getSearchHistory()
    }

    override suspend fun getSearchHistory(keyword: String): List<SearchHistoryLocalDto> {
        TODO("Not yet implemented")
    }

    override suspend fun clearAllSearchHistory() {
        movieDao.clearAllSearchHistory()
    }

    /**
     * Video
     */
    override suspend fun getMovieTrailer(movieId: Int): VideoDto? {
        return movieService.getMovieTrailer(movieId).body()
    }

    /**
     * Watch
     */
    override suspend fun insertMovieWatch(movie: WatchHistoryLocalDto) {
        movieDao.insertWatch(movie)
    }

    override suspend fun deleteMovieWatch(movie: WatchHistoryLocalDto) {
        movieDao.deleteWatch(movie)
        TODO()
    }

    override fun getAllMoviesWatch(): Flow<List<WatchHistoryLocalDto>> {
        return movieDao.getAllWatch()
    }

    /**
     * Rating
     */
    override suspend fun getRatedMovie(): List<RatedMovieDto>? {
        return movieService.getRatedMovie().body()?.results
    }

    override suspend fun setRateMovie(movieId: Int, value: Float): StatusResponse? {
        return movieService.setRateMovie(movieId, value).body()
    }

    override suspend fun deleteRateMovie(movieId: Int): StatusResponse? {
        return movieService.deleteRatingMovie(movieId).body()
    }

    /**
     * List
     */
    override suspend fun createList(sessionId: String, name: String): AddListResponse? {
        return movieService.createList(sessionId, name).body()
    }

    override suspend fun getCreatedList(sessionId: String): List<CreatedListDto>? {
        return movieService.getCreatedList(sessionId = sessionId).body()?.results
    }

    override suspend fun addMovieToList(
        sessionId: String,
        listId: Int,
        movieId: Int
    ): AddMovieDto? {
        return movieService.addMovieToFavList(
            seriesId = sessionId,
            listId = listId,
            movieId = movieId
        ).body()
    }

    override suspend fun getListDetails(listId: Int): FavListDto? {
        return movieService.getList(listId).body()
    }

    override suspend fun getSavedListDetails(listId: Int): List<SavedListDto>? {
        return movieService.getList(listId).body()?.items
    }

    override suspend fun deleteList(listId: Int): StatusResponseDto {
        TODO("Not yet implemented")
    }

//    override suspend fun deleteList(listId: Int): StatusResponse {
//        return statusResponseMapper.map(wrapApiCall { movieService.deleteList(listId) })
//    }


}