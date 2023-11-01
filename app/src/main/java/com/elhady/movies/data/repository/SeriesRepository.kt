package com.elhady.movies.data.repository

import androidx.paging.Pager
import com.elhady.movies.data.local.database.entity.series.AiringTodaySeriesEntity
import com.elhady.movies.data.local.database.entity.series.OnTheAirSeriesEntity
import com.elhady.movies.data.local.database.entity.series.TVSeriesListsEntity
import com.elhady.movies.data.remote.response.tvShow.TVShowDetailsDto
import com.elhady.movies.data.remote.response.tvShow.TVShowDto
import kotlinx.coroutines.flow.Flow

interface SeriesRepository {

    suspend fun getAiringTodaySeries(): Flow<List<AiringTodaySeriesEntity>>

    suspend fun getOnTheAirSeries(): Flow<List<OnTheAirSeriesEntity>>

    fun getAllOnTheAirSeries(): Pager<Int, TVShowDto>

    suspend fun getTVSeriesLists(): Flow<List<TVSeriesListsEntity>>

    fun getAllTopRatedTV(): Pager<Int, TVShowDto>

    fun getAllPopularTV(): Pager<Int, TVShowDto>

    fun getAllLatestTV(): Pager<Int, TVShowDto>

    suspend fun getTVShowDetails(tvShowId: Int): TVShowDetailsDto?
}