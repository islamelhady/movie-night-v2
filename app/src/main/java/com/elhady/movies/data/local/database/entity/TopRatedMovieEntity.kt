package com.elhady.movies.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TOP_RATED_MOVIE_TABLE")
data class TopRatedMovieEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String
)
