package com.elhady.movies.data.repository

import com.elhady.movies.data.local.database.entity.series.AiringTodaySeriesEntity
import com.elhady.movies.data.local.database.entity.series.OnTheAirSeriesEntity
import kotlinx.coroutines.flow.Flow

interface SeriesRepository {

    suspend fun getAiringTodaySeries(): Flow<List<AiringTodaySeriesEntity>>
    suspend fun getOnTheAirSeries(): Flow<List<OnTheAirSeriesEntity>>
}