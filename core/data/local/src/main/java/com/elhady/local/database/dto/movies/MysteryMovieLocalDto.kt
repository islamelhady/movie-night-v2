package com.elhady.local.database.dto.movies

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MYSTERY_MOVIE_TABLE")
data class MysteryMovieLocalDto(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String
)
