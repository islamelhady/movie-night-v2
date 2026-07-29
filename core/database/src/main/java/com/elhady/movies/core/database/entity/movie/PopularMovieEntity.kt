package com.elhady.movies.core.database.entity.movie

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "POPULAR_MOVIE_TABLE")
data class PopularMovieEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val imageUrl: String,
    val rate: Double,
    val title: String,
)
