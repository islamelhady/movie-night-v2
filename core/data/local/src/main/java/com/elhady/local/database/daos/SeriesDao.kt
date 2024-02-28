package com.elhady.local.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elhady.local.database.entity.series.AiringTodaySeriesLocalDto
import com.elhady.local.database.entity.series.OnTheAirSeriesLocalDto
import com.elhady.local.database.entity.series.TVSeriesListsLocalDto

@Dao
interface SeriesDao {

    /**
     *  On The Air Series
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOnTheAirSeries(items: List<OnTheAirSeriesLocalDto>)

    @Query("DELETE FROM ON_THE_AIR_SERIES_TABLE")
    suspend fun deleteOnTheAirSeries()

    @Query("SELECT * FROM ON_THE_AIR_SERIES_TABLE ORDER BY RANDOM()")
    suspend fun getOnTheAirSeries(): List<OnTheAirSeriesLocalDto>

    /**
     *  Airing Today Series
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAiringTodaySeries(items: List<AiringTodaySeriesLocalDto>)

    @Query("DELETE FROM AIRING_TODAY_SERIES_TABLE")
    suspend fun deleteAiringTodaySeries()

    @Query("SELECT * FROM AIRING_TODAY_SERIES_TABLE ORDER BY RANDOM()")
    suspend fun getAiringTodaySeries(): List<AiringTodaySeriesLocalDto>

    /**
     *  TV Series Lists:
     * * Popular
     * * Top Rated
     * * On The Air
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTVSeriesLists(items: List<TVSeriesListsLocalDto>)

    @Query("DELETE FROM TV_SERIES_TABLE")
    suspend fun deleteTVSeriesLists()

    @Query("SELECT * FROM TV_SERIES_TABLE ORDER BY RANDOM()")
    suspend fun getTVSeriesLists(): List<TVSeriesListsLocalDto>
}