package com.elhady.movies.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MYSTERY_MOVIE_TABLE")
data class MysteryMovieEntity(@PrimaryKey val id: Int, val name: String, val imageUrl: String)
