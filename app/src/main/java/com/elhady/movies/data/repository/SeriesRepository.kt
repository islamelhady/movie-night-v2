package com.elhady.movies.data.repository

import androidx.paging.Pager
import com.elhady.movies.data.local.database.entity.series.AiringTodaySeriesEntity
import com.elhady.movies.data.local.database.entity.series.OnTheAirSeriesEntity
import com.elhady.movies.data.local.database.entity.series.TVSeriesListsEntity
import com.elhady.movies.data.remote.response.CreditsDto
import com.elhady.movies.data.remote.response.series.SeriesDetailsDto
import com.elhady.movies.data.remote.response.series.SeriesDto
import kotlinx.coroutines.flow.Flow

interface SeriesRepository {

    suspend fun getAiringTodaySeries(): Flow<List<AiringTodaySeriesEntity>>

    suspend fun getOnTheAirSeries(): Flow<List<OnTheAirSeriesEntity>>

    fun getAllOnTheAirSeries(): Pager<Int, SeriesDto>

    suspend fun getTVSeriesLists(): Flow<List<TVSeriesListsEntity>>

    fun getAllTopRatedTV(): Pager<Int, SeriesDto>

    fun getAllPopularTV(): Pager<Int, SeriesDto>

    fun getAllLatestTV(): Pager<Int, SeriesDto>

    suspend fun getSeriesDetails(seriesId: Int): SeriesDetailsDto?

    suspend fun getSeriesCast(seriesId: Int): CreditsDto?
}