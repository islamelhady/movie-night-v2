package com.elhady.movies.core.database.entity.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "WATCH_HISTORY_MOVIES_TABLE")
data class MovieInWatchHistoryEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val posterPath: String,
    val title: String,
    val voteAverage: Double,
    val description:String,
    val year:Int,
    val dateWatched: Date
)
