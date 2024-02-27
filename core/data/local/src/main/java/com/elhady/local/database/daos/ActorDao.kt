package com.elhady.local.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elhady.local.database.entity.actor.ActorEntity

@Dao
interface ActorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActors(items: List<ActorEntity>)

    @Query("delete from ACTOR_TABLE")
    suspend fun deleteActors()

    @Query("select * from ACTOR_TABLE ORDER BY RANDOM()")
    suspend fun getActors(): List<ActorEntity>

}