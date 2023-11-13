package com.elhady.movies.data.local.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elhady.movies.data.local.database.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(item: SearchHistoryEntity)

    @Delete
    suspend fun deleteSearch(item: SearchHistoryEntity)

    @Query("select * from SEARCH_HISTORY_TABLE")
    fun getAllSearch(): Flow<List<SearchHistoryEntity>>

}