package com.elhady.movies.core.database.entity.movie

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MOVIE_LIST_DETAILS_TABLE")
data class MovieListDetailsEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val imageUrl: String,
    val title: String,
    val rate: Double,
    val year: String,
    val mediaType: String? ="movie"
)
