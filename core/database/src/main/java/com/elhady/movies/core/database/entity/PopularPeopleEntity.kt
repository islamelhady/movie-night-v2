package com.elhady.movies.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "POPULAR_PEOPLE_TABLE")
data class PopularPeopleEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val imagerUrl: String,
    val name: String,
    val popularity: Double
)
