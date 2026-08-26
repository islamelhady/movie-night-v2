package com.elhady.movies.core.database.entity.movie

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MOVIE_TABLE")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val imageUrl: String,
    val title: String,
    val rate: Double,
    val year: String,
    val mediaType: String = "movies",
)
