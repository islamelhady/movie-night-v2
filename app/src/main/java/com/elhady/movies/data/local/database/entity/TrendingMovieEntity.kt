package com.elhady.movies.data.local.database.entity

import androidx.room.Entity

@Entity(tableName = "TRENDING_MOVIE_TABLE")
data class TrendingMovieEntity(val id: Int, val name: String, val imageUrl: String)