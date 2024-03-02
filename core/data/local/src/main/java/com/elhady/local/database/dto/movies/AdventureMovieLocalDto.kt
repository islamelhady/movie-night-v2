package com.elhady.local.database.dto.movies

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ADVENTURE_MOVIE_TABLE")
data class AdventureMovieLocalDto(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String
)
