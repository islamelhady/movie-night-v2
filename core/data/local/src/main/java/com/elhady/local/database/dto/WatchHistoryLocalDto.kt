package com.elhady.local.database.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "WATCH_HISTORY_TABLE")
class WatchHistoryLocalDto(
    @PrimaryKey val id: Int,
    var image: String,
    var title: String,
    var voteAverage: String,
    var movieDuration: Int,
    var releaseDate: String,
    var mediaType:String
)