package com.elhady.movies.core.data.local.database.dto.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "WATCH_HISTORY_MOVIES_TABLE")
data class MovieInWatchHistoryLocalDto(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val posterPath: String,
    val title: String,
    val voteAverage: Double,
    val description:String,
    val year:Int,
    val dateWatched: Date
)
