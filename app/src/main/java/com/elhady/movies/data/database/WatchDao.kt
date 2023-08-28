package com.elhady.movies.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elhady.movies.data.database.entity.WatchList
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(watch: WatchList)

    @Delete
    suspend fun delete(watch: WatchList)

    @Query("SELECT * FROM WATCH_LIST_TABLE")
    fun getAllWatchList(): Flow<List<WatchList>>

}