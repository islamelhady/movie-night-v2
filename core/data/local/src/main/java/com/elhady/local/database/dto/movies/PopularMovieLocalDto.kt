package com.elhady.local.database.dto.movies

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "POPULAR_MOVIE_TABLE")
data class PopularMovieLocalDto(
    @PrimaryKey val id: Int,
    val imageUrl: String,
    val title: String,
    val movieRate: Double,
    val genres: List<String>
)