package com.elhady.movies.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "WATCH_HISTORY_TABLE")
class WatchHistoryEntity(
    @PrimaryKey val id: Int,
    var image: String,
    var title: String,
    var voteAverage: String,
    var movieDuration: Int,
    var releaseDate: String,
    var mediaType:String
)