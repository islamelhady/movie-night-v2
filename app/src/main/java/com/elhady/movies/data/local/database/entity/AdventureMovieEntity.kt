package com.elhady.movies.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ADVENTURE_MOVIE_TABLE")
data class AdventureMovieEntity(@PrimaryKey val id: Int, val name: String, val imageUrl: String)
