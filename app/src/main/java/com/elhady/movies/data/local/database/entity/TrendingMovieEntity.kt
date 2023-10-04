package com.elhady.movies.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TRENDING_MOVIE_TABLE")
data class TrendingMovieEntity(@PrimaryKey val id: Int, val name: String, val imageUrl: String)