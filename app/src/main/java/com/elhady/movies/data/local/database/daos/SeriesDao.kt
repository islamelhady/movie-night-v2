package com.elhady.movies.data.local.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elhady.movies.data.local.database.entity.series.AiringTodaySeriesEntity
import com.elhady.movies.data.local.database.entity.series.OnTheAirSeriesEntity
import com.elhady.movies.data.local.database.entity.series.TVSeriesListsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SeriesDao {

    /**
     *  On The Air Series
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOnTheAirSeries(items: List<OnTheAirSeriesEntity>)

    @Query("DELETE FROM ON_THE_AIR_SERIES_TABLE")
    suspend fun deleteOnTheAirSeries()

    @Query("SELECT * FROM ON_THE_AIR_SERIES_TABLE")
    fun getOnTheAirSeries(): Flow<List<OnTheAirSeriesEntity>>

    /**
     *  Airing Today Series
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAiringTodaySeries(items: List<AiringTodaySeriesEntity>)

    @Query("DELETE FROM AIRING_TODAY_SERIES_TABLE")
    suspend fun deleteAiringTodaySeries()

    @Query("SELECT * FROM AIRING_TODAY_SERIES_TABLE")
    fun getAiringTodaySeries(): Flow<List<AiringTodaySeriesEntity>>

    /**
     *  TV Series Lists:
     * * Popular
     * * Top Rated
     * * On The Air
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTVSeriesLists(items: List<TVSeriesListsEntity>)

    @Query("DELETE FROM TV_SERIES_TABLE")
    suspend fun deleteTVSeriesLists()

    @Query("SELECT * FROM TV_SERIES_TABLE")
    fun getTVSeriesLists(): Flow<List<TVSeriesListsEntity>>
}