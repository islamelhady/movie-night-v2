package com.elhady.movies.core.database.dao.tv

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elhady.movies.core.database.entity.tvshow.TvShowEntity

@Dao
interface TvShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShow(tvShow: List<TvShowEntity>)

    @Query("SELECT * FROM TV_SHOWS_TABLE")
    suspend fun getAllTvShow(): List<TvShowEntity>

    @Query("DELETE FROM TV_SHOWS_TABLE")
    suspend fun clearAllTvShow()
}