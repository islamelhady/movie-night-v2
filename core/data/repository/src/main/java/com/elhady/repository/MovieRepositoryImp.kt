package com.elhady.repository

import androidx.paging.Pager
import com.elhady.entities.MovieEntity
import com.elhady.entities.PopularMovieEntity
import com.elhady.local.AppConfiguration
import com.elhady.local.database.daos.MovieDao
import com.elhady.local.database.entity.SearchHistoryLocalDto
import com.elhady.local.database.entity.WatchHistoryLocalDto
import com.elhady.local.database.entity.movies.AdventureMovieLocalDto
import com.elhady.local.database.entity.movies.MysteryMovieLocalDto
import com.elhady.local.database.entity.movies.NowPlayingMovieLocalDto
import com.elhady.local.database.entity.movies.TopRatedMovieLocalDto
import com.elhady.local.database.entity.movies.TrendingMovieLocalDto
import com.elhady.local.database.entity.movies.UpcomingMovieLocalDto
import com.elhady.local.mappers.movies.AdventureMoviesMapper
import com.elhady.local.mappers.movies.MysteryMoviesMapper
import com.elhady.local.mappers.movies.NowPlayingMovieMapper
import com.elhady.local.mappers.movies.PopularMovieMapper
import com.elhady.local.mappers.movies.TopRatedMovieMapper
import com.elhady.local.mappers.movies.TrendingMovieMapper
import com.elhady.local.mappers.movies.UpcomingMovieMapper
import com.elhady.remote.response.AddListResponse
import com.elhady.remote.response.AddMovieDto
import com.elhady.remote.response.CreatedListDto
import com.elhady.remote.response.CreditsDto
import com.elhady.remote.response.FavListDto
import com.elhady.remote.response.RatedMovieDto
import com.elhady.remote.response.RatingDto
import com.elhady.remote.response.SavedListDto
import com.elhady.remote.response.StatusResponseDto
import com.elhady.remote.response.dto.MovieDto
import com.elhady.remote.response.genre.GenreDto
import com.elhady.remote.response.movie.MovieDetailsDto
import com.elhady.remote.response.review.ReviewDto
import com.elhady.remote.response.video.VideoDto
import com.elhady.remote.serviece.MovieService
import com.elhady.repository.mappers.domain.DomainPopularMovieMapper
import com.elhady.repository.mappers.domain.DomainUpcomingMovieMapper
import com.elhady.repository.mediaDataSource.movies.MovieDataSourceContainer
import com.elhady.repository.searchDataSource.MovieSearchDataSource
import com.elhady.usecase.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
    private val movieService: MovieService,
    private val domainPopularMovieMapper: DomainPopularMovieMapper,
    private val domainUpcomingMovieMapper: DomainUpcomingMovieMapper,
    private val popularMovieMapper: PopularMovieMapper,
    private val movieDao: MovieDao,
    private val appConfiguration: AppConfiguration,
    private val trendingMovieMapper: TrendingMovieMapper,
    private val upcomingMovieMapper: UpcomingMovieMapper,
    private val nowPlayingMovieMapper: NowPlayingMovieMapper,
    private val topRatedMovieMapper: TopRatedMovieMapper,
    private val mysteryMoviesMapper: MysteryMoviesMapper,
//    private val statusResponseMapper: StatusResponseMapper,
    private val adventureMoviesMapper: AdventureMoviesMapper,
    private val movieDataSourceContainer: MovieDataSourceContainer,
    private val movieSearchDataSource: MovieSearchDataSource
) : BaseRepository(), MovieRepository {

    /**
     *  Popular Movies
     */
    override suspend fun getPopularMovies(): List<PopularMovieEntity> {
        refreshOneTimePerDay(
            appConfiguration.getRequestDate(Constant.POPULAR_MOVIE_REQUEST_DATE_KEY),
            ::refreshPopularMovies
        )
        return domainPopularMovieMapper.map( movieDao.getPopularMovies())
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
     *  Genre Movies List
     */
    override suspend fun getGenreMovies(): List<GenreDto>? {
        return movieService.getGenreMovies().body()?.genres
    }

    /**
     *  Movies By Genre
     */
    override fun getMoviesByGenre(genreId: Int): Pager<Int, MovieDto> {
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

    override suspend fun getUpcomingMovies(): List<MovieEntity> {
        refreshOneTimePerDay(
            appConfiguration.getRequestDate(Constant.UPCOMING_MOVIE_REQUEST_DATE_KEY),
            ::refreshUpcomingMovies
        )
        return domainUpcomingMovieMapper.map(movieDao.getUpcomingMovies())
    }

    private suspend fun refreshUpcomingMovies(currentDate: Date) {
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
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { movieDataSourceContainer.upcomingMovieDataSource })
    }

    /**
     *  Top Rated Movies
     */
    override suspend fun getTopRatedMovies(): List<TopRatedMovieLocalDto> {
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
     *  All Top Rated Movies
     */
    override fun getAllTopRatedMovies(): Pager<Int, MovieDto> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { movieDataSourceContainer.topRatedMovieDataSource })
    }

    /**
     *  Now Playing Movies
     */
    override suspend fun getNowPlayingMovies(): List<NowPlayingMovieLocalDto> {
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
    override fun getAllTrendingMovies(): Pager<Int, MovieDto> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { movieDataSourceContainer.trendingMovieDataSource })
    }

    /**
     *  Mystery Movies
     */
    override suspend fun getMysteryMovies(): List<MysteryMovieLocalDto> {
        refreshOneTimePerDay(
            appConfiguration.getRequestDate(Constant.MYSTERY_MOVIE_REQUEST_DATE_KEY),
            ::refreshMysteryMovies
        )
        return movieDao.getMysteryMovies()
    }

    suspend fun refreshMysteryMovies(currentDate: Date) {
        wrap(
            { movieService.getMoviesListByGenre(genreID = Constant.MYSTERY_ID) },
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
    override fun getAllAdventureMovies(): Pager<Int, MovieDto> {
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

    override suspend fun getSimilarMovies(movieId: Int): List<MovieDto>? {
        return movieService.getSimilarMovie(movieId = movieId).body()?.items
    }

    override suspend fun getMovieReview(movieId: Int): List<ReviewDto>? {
        return movieService.getMovieReview(movieId).body()?.items
    }

    /**
     *  All Movies
     */
    override fun getAllMovies(): Pager<Int, MovieDto> {
        return Pager(config = pagingConfig, pagingSourceFactory = { movieDataSourceContainer.moviesDataSource })
    }

    /**
     * Serach
     * * movies
     * * history
     */
    override suspend fun searchForMoviesPager(query: String): Pager<Int, MovieDto> {
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
        return movieService.getRatedMovie().body()?.items
    }

    override suspend fun setRateMovie(movieId: Int, value: Float): RatingDto? {
        return movieService.setRateMovie(movieId, value).body()
    }

    override suspend fun deleteRateMovie(movieId: Int): RatingDto? {
        return movieService.deleteRatingMovie(movieId).body()
    }

    /**
     * List
     */
    override suspend fun createList(sessionId: String, name: String): AddListResponse? {
        return movieService.createList(sessionId, name).body()
    }

    override suspend fun getCreatedList(sessionId: String): List<CreatedListDto>? {
        return movieService.getCreatedList(sessionId = sessionId).body()?.items
    }

    override suspend fun addMovieToList(
        sessionId: String,
        listId: Int,
        movieId: Int
    ): AddMovieDto? {
        return movieService.addMovieToFavList(seriesId = sessionId, listId = listId, movieId = movieId).body()
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