package com.elhady.movies.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.elhady.movies.data.database.entity.SearchHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {

    @Insert
    suspend fun insert(search: SearchHistory)

    @Delete
    suspend fun delete(search: SearchHistory)

    @Query("SELECT * FROM SEARCH_HISTORY_TABLE")
    fun getAllHistorySearch(): Flow<List<SearchHistory>>
}