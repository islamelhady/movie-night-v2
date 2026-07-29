package com.elhady.movies.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elhady.movies.core.database.entity.people.PopularPeopleEntity

@Dao
interface PeopleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularPeople(people: List<PopularPeopleEntity>)

    @Query("select * from POPULAR_PEOPLE_TABLE ORDER BY RANDOM()")
    suspend fun getPopularPeople(): List<PopularPeopleEntity>

    @Query("delete from POPULAR_PEOPLE_TABLE")
    suspend fun clearAllPopularPeople()
}