package com.elhady.movies.core.database.entity.genre

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "GENRES_TVS_TABLE")
data class GenresTvEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String
)