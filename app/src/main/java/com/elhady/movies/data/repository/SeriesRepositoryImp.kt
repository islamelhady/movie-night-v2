package com.elhady.movies.data.repository

import androidx.paging.Pager
import com.elhady.movies.data.Constant
import com.elhady.movies.data.local.AppConfiguration
import com.elhady.movies.data.local.database.daos.MovieDao
import com.elhady.movies.data.local.database.daos.SeriesDao
import com.elhady.movies.data.local.database.entity.WatchHistoryEntity
import com.elhady.movies.data.local.database.entity.series.AiringTodaySeriesEntity
import com.elhady.movies.data.local.database.entity.series.OnTheAirSeriesEntity
import com.elhady.movies.data.local.database.entity.series.TVSeriesListsEntity
import com.elhady.movies.data.local.mappers.series.AiringTodaySeriesMapper
import com.elhady.movies.data.local.mappers.series.OnTheAirSeriesMapper
import com.elhady.movies.data.local.mappers.series.TVSeriesListsMapper
import com.elhady.movies.data.remote.response.CreditsDto
import com.elhady.movies.data.remote.response.RatedSeriesDto
import com.elhady.movies.data.remote.response.RatingDto
import com.elhady.movies.data.remote.response.TrendingDto
import com.elhady.movies.data.remote.response.episode.EpisodeDto
import com.elhady.movies.data.remote.response.genre.GenreDto
import com.elhady.movies.data.remote.response.movie.MovieDto
import com.elhady.movies.data.remote.response.review.ReviewDto
import com.elhady.movies.data.remote.response.series.SeriesDetailsDto
import com.elhady.movies.data.remote.response.series.SeriesDto
import com.elhady.movies.data.remote.response.video.VideoDto
import com.elhady.movies.data.remote.service.MovieService
import com.elhady.movies.data.repository.mediaDataSource.series.SeriesDataSourceContainer
import com.elhady.movies.data.repository.searchDataSource.SeriesSearchDataSource
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class SeriesRepositoryImp @Inject constructor(
    private val movieService: MovieService,
    private val onTheAirSeriesMapper: OnTheAirSeriesMapper,
    private val airingSeriesMapper: AiringTodaySeriesMapper,
    private val tvSeriesListsMapper: TVSeriesListsMapper,
    private val seriesDao: SeriesDao,
    private val movieDao: MovieDao,
    private val appConfiguration: AppConfiguration,
    private val seriesDataSourceContainer: SeriesDataSourceContainer,
    private val seriesSearchDataSource: SeriesSearchDataSource
) : BaseRepository(), SeriesRepository {

    /**
     *  Airing Today Series
     */
    override suspend fun getAiringTodaySeries(): Flow<List<AiringTodaySeriesEntity>> {
        refreshOneTimePerDay(
            appConfiguration.getRequestDate(Constant.AIRING_TODAY_SERIES_REQUEST_DATE_KEY),
            ::refreshAiringTodaySeries
        )
        return seriesDao.getAiringTodaySeries()
    }

    private suspend fun refreshAiringTodaySeries(currentDate: Date) {
        wrap(
            { movieService.getAiringTodayTV() },
            { list ->
                list?.map {
                    airingSeriesMapper.map(it)
                }
            },
            {
                seriesDao.deleteAiringTodaySeries()
                seriesDao.insertAiringTodaySeries(it)
                appConfiguration.saveRequestDate(
                    Constant.AIRING_TODAY_SERIES_REQUEST_DATE_KEY,
                    currentDate.time
                )
            }
        )
    }

    /**
     *  On The Air Series
     */
    override suspend fun getOnTheAirSeries(): Flow<List<OnTheAirSeriesEntity>> {
        refreshOneTimePerDay(
            appConfiguration.getRequestDate(Constant.ON_THE_AIR_SERIES_REQUEST_DATE_KEY),
            ::refreshOnTheAirSeries
        )
        return seriesDao.getOnTheAirSeries()
    }

    private suspend fun refreshOnTheAirSeries(currentDate: Date) {
        wrap(
            { movieService.getOnTheAirTV() },
            { list ->
                list?.map {
                    onTheAirSeriesMapper.map(it)
                }
            },
            {
                seriesDao.deleteOnTheAirSeries()
                seriesDao.insertOnTheAirSeries(it)
                appConfiguration.saveRequestDate(
                    Constant.ON_THE_AIR_SERIES_REQUEST_DATE_KEY,
                    currentDate.time
                )
            }
        )
    }

    /**
     *  All On The Air Series
     */
    override fun getAllOnTheAirSeries(): Pager<Int, SeriesDto> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { seriesDataSourceContainer.onTheAirTVDataSource })
    }

    /**
     *  TV Series Lists
     * * Popular
     * * Top Rated
     * * Airing Today
     */
    override suspend fun getTVSeriesLists(): Flow<List<TVSeriesListsEntity>> {
        refreshOneTimePerDay(
            appConfiguration.getRequestDate(Constant.TV_SERIES_LISTS_REQUEST_DATE_KEY),
            ::refreshTVSeriesLists
        )
        return seriesDao.getTVSeriesLists()
    }

    private suspend fun refreshTVSeriesLists(currentDate: Date) {
        val items = mutableListOf<TVSeriesListsEntity>()
        movieService.getPopularTV().body()?.items?.first()?.let {
            items.add(tvSeriesListsMapper.map(it))
        }
        movieService.getTopRatedTV().body()?.items?.first()?.let {
            items.add(tvSeriesListsMapper.map(it))
        }
        movieService.getAiringTodayTV().body()?.items?.first()?.let {
            items.add(tvSeriesListsMapper.map(it))
        }
        seriesDao.deleteTVSeriesLists()
        seriesDao.insertTVSeriesLists(items)
        appConfiguration.saveRequestDate(
            Constant.TV_SERIES_LISTS_REQUEST_DATE_KEY,
            currentDate.time
        )
    }

    /**
     *  All Top Rated TV
     */
    override fun getAllTopRatedTV(): Pager<Int, SeriesDto> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { seriesDataSourceContainer.topRatedTVDataSource })
    }

    /**
     *  All Popular TV
     */
    override fun getAllPopularTV(): Pager<Int, SeriesDto> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { seriesDataSourceContainer.popularTVDataSource })
    }

    /**
     *  All Latest TV
     */
    override fun getAllLatestTV(): Pager<Int, SeriesDto> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { seriesDataSourceContainer.latestTVDataSource })
    }

    /**
     *  Details Series
     * * Details
     * * Cast
     * * Similar Series
     * * Details season (List Episode)
     */
    override suspend fun getSeriesDetails(seriesId: Int): SeriesDetailsDto? {
        return movieService.getSeriesDetails(seriesId).body()
    }

    override suspend fun getSeriesCast(seriesId: Int): CreditsDto? {
        return movieService.getSeriesCast(seriesId).body()
    }

    override suspend fun getSimilarSeries(seriesId: Int): List<SeriesDto>? {
        return movieService.getSimilarSeries(seriesId).body()?.items
    }

    override suspend fun getSeriesReview(seriesId: Int): List<ReviewDto>? {
        return movieService.getSeriesReview(seriesId).body()?.items
    }

    override suspend fun getSeasonDetails(seriesId: Int, seasonNumber: Int): List<EpisodeDto>? {
        return movieService.getSeasonDetails(seriesId = seriesId, seasonNumber = seasonNumber)
            .body()?.episodes
    }

    /**
     * Trending
     */
    override suspend fun getTrendingTvSries(): List<TrendingDto>? {
        return movieService.getTrending().body()?.items
    }

    /**
     * Genre Series
     */
    override suspend fun getGenreSeries(): List<GenreDto>? {
        return movieService.getGenreSeries().body()?.genres
    }

    /**
     * Series By Genre
     */
    override fun getSeriesByGenre(genreId: Int): Pager<Int, SeriesDto> {
        return Pager(config = pagingConfig, pagingSourceFactory = {
            val seriesDataSource = seriesDataSourceContainer.seriesByGenreDataSource
            seriesDataSource.setGenre(genreId)
            seriesDataSource
        })
    }

    /**
     * All Series
     */
    override fun getAllSeries(): Pager<Int, SeriesDto> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { seriesDataSourceContainer.seriesDataSource })
    }

    /**
     *  Search series
     */
    override suspend fun searchForSeriesPager(query: String): Pager<Int, SeriesDto> {
        val dataSource = seriesSearchDataSource
        dataSource.setSearch(query)
        return Pager(config = pagingConfig, pagingSourceFactory = { dataSource })
    }

    /**
     * Video
     */
    override suspend fun getSeriesTrailer(seriesId: Int): VideoDto? {
        return movieService.getSeriesTrailer(seriesId).body()
    }

    /**
     * Watch
     */
    override suspend fun insertSeriesWatch(series: WatchHistoryEntity) {
        movieDao.insertWatch(series)
    }

    /**
     * Rating
     */
    override suspend fun setRatingSeries(seriesId: Int, value: Float): RatingDto? {
        return movieService.setRatingSeries(seriesId = seriesId, rating = value).body()
    }

    override suspend fun deleteRateSeries(seriesId: Int): RatingDto? {
        return movieService.deleteRatingSeries(seriesId).body()
    }

    override suspend fun getRatedSeries(): List<RatedSeriesDto>? {
        return movieService.getRatedTvShow().body()?.items
    }


}