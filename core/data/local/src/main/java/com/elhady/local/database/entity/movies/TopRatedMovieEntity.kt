package com.elhady.local.database.entity.movies

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TOP_RATED_MOVIE_TABLE")
data class TopRatedMovieEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String
)
