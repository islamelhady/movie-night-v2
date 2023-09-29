package com.elhady.movies.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "POPULAR_MOVIE_TABLE")
data class PopularMovieEntity(
    @PrimaryKey val id: Int,
    val imageUrl: String,
    val title: String,
    val movieRate: Double
)