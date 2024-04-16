package com.elhady.movies.core.data.local.database.dto.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.elhady.movies.core.data.local.database.dto.GenresMoviesLocalDto

@Entity(tableName = "TRENDING_MOVIES_TABLE")
data class TrendingMoviesLocalDto(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val imageUrl: String,
    val rate: Double,
    val title: String,
    val year: String,
    val genres: List<String>
)
