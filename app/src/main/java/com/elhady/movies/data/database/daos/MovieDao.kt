package com.elhady.movies.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elhady.movies.data.database.entity.SearchHistory
import com.elhady.movies.data.database.entity.WatchList
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: WatchList)

    @Delete
    suspend fun delete(list: WatchList)

    @Query("SELECT * FROM WATCH_LIST_TABLE")
    fun getAllSavedMovies(): Flow<List<WatchList>>


    @Insert
    suspend fun insert(search: SearchHistory)

    @Delete
    suspend fun delete(search: SearchHistory)

    @Query("SELECT * FROM SEARCH_HISTORY_TABLE")
    fun getAllSearchHistory(): Flow<List<SearchHistory>>
}