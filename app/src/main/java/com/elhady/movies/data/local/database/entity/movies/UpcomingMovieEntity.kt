package com.elhady.movies.data.local.database.entity.movies

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UPCOMING_MOVIE_TABLE")
data class UpcomingMovieEntity(@PrimaryKey val id: Int, val title: String, val imageUrl: String)