package com.elhady.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elhady.local.database.dto.actor.ActorLocalDto

@Dao
interface ActorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActors(items: List<ActorLocalDto>)

    @Query("delete from ACTOR_TABLE")
    suspend fun deleteActors()

    @Query("select * from ACTOR_TABLE ORDER BY RANDOM()")
    suspend fun getActors(): List<ActorLocalDto>

}