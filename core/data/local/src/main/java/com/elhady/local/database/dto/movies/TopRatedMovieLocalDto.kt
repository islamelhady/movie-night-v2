package com.elhady.local.database.dto.movies

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TOP_RATED_MOVIE_TABLE")
data class TopRatedMovieLocalDto(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String
)
