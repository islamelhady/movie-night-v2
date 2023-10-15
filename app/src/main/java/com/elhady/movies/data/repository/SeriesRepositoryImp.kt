package com.elhady.movies.data.repository

import com.elhady.movies.data.Constants
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
            appConfiguration.getRequestDate(Constants.AIRING_TODAY_SERIES_REQUEST_DATE_KEY),
            ::refreshAiringTodaySeries
        )
        return seriesDao.getAiringTodaySeries()
    }

    private suspend fun refreshAiringTodaySeries(currentDate: Date) {
        wrap(
            {
                movieService.getAiringTodayTV()
            },
            { items ->
                items?.map {
                    airingSeriesMapper.map(it)
                }
            },
            {
                seriesDao.deleteAiringTodaySeries()
                seriesDao.insertAiringTodaySeries(it)
                appConfiguration.saveRequestDate(
                    Constants.AIRING_TODAY_SERIES_REQUEST_DATE_KEY,
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
            appConfiguration.getRequestDate(Constants.ON_THE_AIR_SERIES_REQUEST_DATE_KEY),
            ::refreshOnTheAirSeries
        )
        return seriesDao.getOnTheAirSeries()
    }

    private suspend fun refreshOnTheAirSeries(currentDate: Date) {
        wrap(
            {
                movieService.getOnTheAirTV()
            },
            { items ->
                items?.map {
                    onTheAirSeriesMapper.map(it)
                }
            },
            {
                seriesDao.deleteOnTheAirSeries()
                seriesDao.insertOnTheAirSeries(it)
                appConfiguration.saveRequestDate(
                    Constants.ON_THE_AIR_SERIES_REQUEST_DATE_KEY,
                    currentDate.time
                )
            })
    }

    override suspend fun getTVSeriesLists(): Flow<List<TVSeriesListsEntity>> {
        refreshOneTimePerDay(appConfiguration.getRequestDate(Constants.TV_SERIES_LISTS_REQUEST_DATE_KEY), ::refreshTVSeriesLists)
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
        appConfiguration.saveRequestDate(Constants.TV_SERIES_LISTS_REQUEST_DATE_KEY, currentDate.time)
    }

}