package com.elhady.local.database.dto.movies

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UPCOMING_MOVIE_TABLE")
data class UpcomingMovieLocalDto(
    @PrimaryKey val id: Int,
    val title: String,
    val imageUrl: String
)