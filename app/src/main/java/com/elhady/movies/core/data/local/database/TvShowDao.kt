package com.elhady.movies.core.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elhady.movies.core.data.local.database.dto.tvshow.AiringTodayTvShowsLocalDto
import com.elhady.movies.core.data.local.database.dto.tvshow.TvShowsLocalDto

@Dao
interface TvShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShow(tvShow: List<TvShowsLocalDto>)

    @Query("SELECT * FROM TV_SHOWS_TABLE")
    suspend fun getAllTvShow(): List<TvShowsLocalDto>

    @Query("DELETE FROM TV_SHOWS_TABLE")
    suspend fun clearAllTvShow()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAiringTodayTvShow(tvShow: List<AiringTodayTvShowsLocalDto>)

    @Query("SELECT * FROM AIRING_TODAY_TV_SHOWS_TABLE ORDER BY RANDOM()")
    suspend fun getAllAiringTodayTvShow(): List<AiringTodayTvShowsLocalDto>

    @Query("DELETE FROM AIRING_TODAY_TV_SHOWS_TABLE")
    suspend fun clearAllAiringTodayTvShow()
}