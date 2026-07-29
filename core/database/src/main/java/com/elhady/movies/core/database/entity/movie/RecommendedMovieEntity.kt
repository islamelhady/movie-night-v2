package com.elhady.movies.core.database.entity.movie

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RECOMMENDED_MOVIE_TABLE")
data class RecommendedMovieEntity (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val imageUrl: String,
    val rate: Double,
    val title: String,
)
