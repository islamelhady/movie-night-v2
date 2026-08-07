package com.elhady.movies.core.database.dao.search

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elhady.movies.core.database.entity.search.SearchHistoryEntity

@Dao
interface SearchHistoryDao {

    @Query("select * from SEARCH_HISTORY_TABLE WHERE keyword LIKE :keyword")
    suspend fun getSearchHistory(keyword: String): List<SearchHistoryEntity>

    @Query("select * from SEARCH_HISTORY_TABLE ORDER BY keyword ASC LIMIT 10")
    suspend fun getSearchHistory(): List<SearchHistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchHistory(searchHistory: SearchHistoryEntity)

    @Query("delete from SEARCH_HISTORY_TABLE")
    suspend fun clearAllSearchHistory()

    @Query("delete from SEARCH_HISTORY_TABLE where keyword like :keyword")
    suspend fun deleteSearchHistory(keyword: String)
}