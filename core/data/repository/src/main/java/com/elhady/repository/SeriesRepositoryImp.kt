package com.elhady.repository

import androidx.paging.Pager
import com.elhady.entities.TvShowEntity
import com.elhady.local.AppConfiguration
import com.elhady.local.database.dao.MovieDao
import com.elhady.local.database.dao.SeriesDao
import com.elhady.local.database.dto.WatchHistoryLocalDto
import com.elhady.local.database.dto.series.TVSeriesListsLocalDto
import com.elhady.repository.mappers.cash.series.LocalAiringTodayTVShowsMapper
import com.elhady.repository.mappers.cash.series.LocalOnTheAirTVShowsMapper
import com.elhady.repository.mappers.cash.series.LocalTVShowsListsMapper
import com.elhady.remote.response.CreditsDto
import com.elhady.remote.response.RatedSeriesDto
import com.elhady.remote.response.StatusResponse
import com.elhady.remote.response.TrendingDto
import com.elhady.remote.response.episode.EpisodeDto
import com.elhady.remote.response.genre.GenreMovieRemoteDto
import com.elhady.remote.response.review.ReviewDto
import com.elhady.remote.response.dto.tvShowDetails.SeriesDetailsDto
import com.elhady.remote.response.dto.TVShowsRemoteDto
import com.elhady.remote.response.video.VideoDto
import com.elhady.remote.serviece.MovieService
import com.elhady.repository.mappers.domain.series.DomainAiringTodayTVShowsMapper
import com.elhady.repository.mappers.domain.series.DomainOnTheAirTVShowMapper
import com.elhady.repository.mappers.domain.series.DomainTVShowsListsMapper
import com.elhady.repository.mediaDataSource.series.SeriesDataSourceContainer
import com.elhady.repository.searchDataSource.SeriesSearchDataSource
import com.elhady.usecase.repository.SeriesRepository
import java.util.Random
import javax.inject.Inject

class SeriesRepositoryImp @Inject constructor(
    private val service: MovieService,
    private val random: Random,
    private val localAiringTodayTVShowsMapper: LocalAiringTodayTVShowsMapper,
    private val domainAiringTodayTVShowsMapper: DomainAiringTodayTVShowsMapper,
    private val domainOnTheAirTVShowMapper: DomainOnTheAirTVShowMapper,
    private val localOnTheAirTVShowsMapper: LocalOnTheAirTVShowsMapper,
    private val localTVShowsListsMapper: LocalTVShowsListsMapper,
    private val domainTVShowsListsMapper: DomainTVShowsListsMapper,
    private val seriesDao: SeriesDao,
    private val movieDao: MovieDao,
    private val appConfiguration: AppConfiguration,
    private val seriesDataSourceContainer: SeriesDataSourceContainer,
    private val seriesSearchDataSource: SeriesSearchDataSource
) : BaseRepository(), SeriesRepository {

    /**
     *  Airing Today TV shows
     */
    override suspend fun getAiringTodayTVShowsFromDatabase(): List<TvShowEntity> {
        return domainAiringTodayTVShowsMapper.map(seriesDao.getAiringTodaySeries())
    }


    override suspend fun refreshAiringTodayTVShows() {
        refreshWrapper(
            { service.getAiringTodayTV(page = random.nextInt(20) + 1) },
            localAiringTodayTVShowsMapper::map,
            seriesDao::deleteAiringTodaySeries,
            seriesDao::insertAiringTodaySeries
        )
    }

    /**
     *  On The Air TV shows
     */

    override suspend fun getOnTheAirTVShowsFromDatabase(): List<TvShowEntity> {
        return domainOnTheAirTVShowMapper.map(seriesDao.getOnTheAirSeries())
    }

    override suspend fun refreshOnTheAirTVShowsTVShows() {
        refreshWrapper(
            { service.getOnTheAirTV(page = random.nextInt(20)+1) },
            localOnTheAirTVShowsMapper::map,
            seriesDao::deleteOnTheAirSeries,
            seriesDao::insertOnTheAirSeries
        )
    }

    /**
     *  All On The Air Series
     */
//    override fun getAllOnTheAirSeries(): Pager<Int, TVShowsRemoteDto> {
//        return Pager(
//            config = pagingConfig,
//            pagingSourceFactory = { seriesDataSourceContainer.onTheAirTVDataSource })
//    }

    /**
     *  TV Shows Lists
     * * Popular
     * * Top Rated
     * * Airing Today
     */

    override suspend fun getTVShowsListsFromDatabase(): List<TvShowEntity> {
        return domainTVShowsListsMapper.map(seriesDao.getTVSeriesLists())
    }

    override suspend fun refreshTVShowsLists() {
        val items = mutableListOf<TVSeriesListsLocalDto>()
        service.getPopularTV().body()?.results?.first()?.let {
            items.add(localTVShowsListsMapper.map(it))
        }
        service.getTopRatedTV().body()?.results?.first()?.let {
            items.add(localTVShowsListsMapper.map(it))
        }
        service.getAiringTodayTV().body()?.results?.first()?.let {
            items.add(localTVShowsListsMapper.map(it))
        }
        seriesDao.deleteTVSeriesLists()
        seriesDao.insertTVSeriesLists(items)
    }

    /**
     *  All Top Rated TV
     */
//    override fun getAllTopRatedTV(): Pager<Int, TVShowsRemoteDto> {
//        return Pager(
//            config = pagingConfig,
//            pagingSourceFactory = { seriesDataSourceContainer.topRatedTVDataSource })
//    }

    /**
     *  All Popular TV
     */
//    override fun getAllPopularTV(): Pager<Int, TVShowsRemoteDto> {
//        return Pager(
//            config = pagingConfig,
//            pagingSourceFactory = { seriesDataSourceContainer.popularTVDataSource })
//    }

    /**
     *  All Latest TV
     */
//    override fun getAllLatestTV(): Pager<Int, TVShowsRemoteDto> {
//        return Pager(
//            config = pagingConfig,
//            pagingSourceFactory = { seriesDataSourceContainer.latestTVDataSource })
//    }

    /**
     *  Details Series
     * * Details
     * * Cast
     * * Similar Series
     * * Details season (List Episode)
     */
//    override suspend fun getSeriesDetails(seriesId: Int): SeriesDetailsDto? {
//        return movieService.getSeriesDetails(seriesId).body()
//    }
//
//    override suspend fun getSeriesCast(seriesId: Int): CreditsDto? {
//        return movieService.getSeriesCast(seriesId).body()
//    }
//
//    override suspend fun getSimilarSeries(seriesId: Int): List<TVShowsRemoteDto>? {
//        return movieService.getSimilarSeries(seriesId).body()?.results
//    }
//
//    override suspend fun getSeriesReview(seriesId: Int): List<ReviewDto>? {
//        return movieService.getSeriesReview(seriesId).body()?.results
//    }
//
//    override suspend fun getSeasonDetails(seriesId: Int, seasonNumber: Int): List<EpisodeDto>? {
//        return movieService.getSeasonDetails(seriesId = seriesId, seasonNumber = seasonNumber)
//            .body()?.episodes
//    }

    /**
     * Trending
     */
//    override suspend fun getTrendingTvSries(): List<TrendingDto>? {
//        return service.getTrending().body()?.results
//    }

    /**
     * Genre Series
     */
//    override suspend fun getGenreSeries(): List<GenreMovieRemoteDto>? {
//        return movieService.getGenreSeries().body()?.genres
//    }

    /**
     * Series By Genre
     */
//    override fun getSeriesByGenre(genreId: Int): Pager<Int, TVShowsRemoteDto> {
//        return Pager(config = pagingConfig, pagingSourceFactory = {
//            val seriesDataSource = seriesDataSourceContainer.seriesByGenreDataSource
//            seriesDataSource.setGenre(genreId)
//            seriesDataSource
//        })
//    }

    /**
     * All Series
     */
//    override fun getAllSeries(): Pager<Int, TVShowsRemoteDto> {
//        return Pager(
//            config = pagingConfig,
//            pagingSourceFactory = { seriesDataSourceContainer.seriesDataSource })
//    }

    /**
     *  Search series
     */
//    override suspend fun searchForSeriesPager(query: String): Pager<Int, TVShowsRemoteDto> {
//        val dataSource = seriesSearchDataSource
//        dataSource.setSearch(query)
//        return Pager(config = pagingConfig, pagingSourceFactory = { dataSource })
//    }

    /**
     * Video
     */
//    override suspend fun getSeriesTrailer(seriesId: Int): VideoDto? {
//        return movieService.getSeriesTrailer(seriesId).body()
//    }

    /**
     * Watch
     */
//    override suspend fun insertSeriesWatch(series: WatchHistoryLocalDto) {
//        movieDao.insertWatch(series)
//    }

    /**
     * Rating
     */
//    override suspend fun setRatingSeries(seriesId: Int, value: Float): StatusResponse? {
//        return movieService.setRatingSeries(seriesId = seriesId, rating = value).body()
//    }
//
//    override suspend fun deleteRateSeries(seriesId: Int): StatusResponse? {
//        return movieService.deleteRatingSeries(seriesId).body()
//    }
//
//    override suspend fun getRatedSeries(): List<RatedSeriesDto>? {
//        return movieService.getRatedTvShow().body()?.results
//    }


}