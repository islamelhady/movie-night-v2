package com.elhady.movies.core.database.entity.movie

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TRENDING_MOVIES_TABLE")
data class TrendingMoviesEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val imageUrl: String,
    val rate: Double,
    val title: String,
    val year: String,
    val genres: List<String>
)
