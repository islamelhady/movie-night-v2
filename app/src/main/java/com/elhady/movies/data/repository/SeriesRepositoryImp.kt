package com.elhady.movies.data.repository

import com.elhady.movies.data.Constants
import com.elhady.movies.data.local.AppConfiguration
import com.elhady.movies.data.local.database.daos.SeriesDao
import com.elhady.movies.data.local.database.entity.series.AiringTodaySeriesEntity
import com.elhady.movies.data.local.database.entity.series.OnTheAirSeriesEntity
import com.elhady.movies.data.local.mappers.series.AiringTodaySeriesMapper
import com.elhady.movies.data.local.mappers.series.OnTheAirSeriesMapper
import com.elhady.movies.data.remote.service.MovieService
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class SeriesRepositoryImp @Inject constructor(
    private val movieService: MovieService,
    private val onTheAirSeriesMapper: OnTheAirSeriesMapper,
    private val airingSeriesMapper: AiringTodaySeriesMapper,
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

}