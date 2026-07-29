package com.elhady.movies.core.database.entity.movie

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WATCHLIST_TABLE")
data class WatchlistEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val imageUrl: String,
    val title: String,
    val rate: Double,
    val year: String,
    val mediaType: String = "movies",
)
