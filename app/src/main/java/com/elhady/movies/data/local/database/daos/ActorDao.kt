package com.elhady.movies.data.local.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elhady.movies.data.local.database.entity.actor.ActorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActors(items: List<ActorEntity>)

    @Query("delete from ACTOR_TABLE")
    suspend fun deleteActors()

    @Query("select * from ACTOR_TABLE")
    fun getActors(): Flow<List<ActorEntity>>

}