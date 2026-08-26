package com.elhady.movies.core.database.dao.tv

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elhady.movies.core.database.entity.tvshow.AiringTodayTvShowEntity

@Dao
interface AiringTodayTvShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAiringTodayTvShow(tvShow: List<AiringTodayTvShowEntity>)

    @Query("SELECT * FROM AIRING_TODAY_TV_SHOWS_TABLE ORDER BY RANDOM()")
    suspend fun getAllAiringTodayTvShow(): List<AiringTodayTvShowEntity>

    @Query("DELETE FROM AIRING_TODAY_TV_SHOWS_TABLE")
    suspend fun clearAllAiringTodayTvShow()
}