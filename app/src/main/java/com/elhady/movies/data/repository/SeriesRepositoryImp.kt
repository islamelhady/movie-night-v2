package com.elhady.movies.data.repository

import com.elhady.movies.data.Constant
import com.elhady.movies.data.local.AppConfiguration
import com.elhady.movies.data.local.database.daos.SeriesDao
import com.elhady.movies.data.local.database.entity.series.AiringTodaySeriesEntity
import com.elhady.movies.data.local.database.entity.series.OnTheAirSeriesEntity
import com.elhady.movies.data.local.database.entity.series.TVSeriesListsEntity
import com.elhady.movies.data.local.mappers.series.AiringTodaySeriesMapper
import com.elhady.movies.data.local.mappers.series.OnTheAirSeriesMapper
import com.elhady.movies.data.local.mappers.series.TVSeriesListsMapper
import com.elhady.movies.data.remote.service.MovieService
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class SeriesRepositoryImp @Inject constructor(
    private val movieService: MovieService,
    private val onTheAirSeriesMapper: OnTheAirSeriesMapper,
    private val airingSeriesMapper: AiringTodaySeriesMapper,
    private val tvSeriesListsMapper: TVSeriesListsMapper,
    private val seriesDao: SeriesDao,
    private val appConfiguration: AppConfiguration
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
            })
    }

    /**
     *  TV Series Lists
     * * Popular
     * * Top Rated
     * * Airing Today
     */
    override suspend fun getTVSeriesLists(): Flow<List<TVSeriesListsEntity>> {
        refreshOneTimePerDay(appConfiguration.getRequestDate(Constant.TV_SERIES_LISTS_REQUEST_DATE_KEY), ::refreshTVSeriesLists)
        return seriesDao.getTVSeriesLists()
    }

    private suspend fun refreshTVSeriesLists(currentDate: Date){
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
        appConfiguration.saveRequestDate(Constant.TV_SERIES_LISTS_REQUEST_DATE_KEY, currentDate.time)
    }

}