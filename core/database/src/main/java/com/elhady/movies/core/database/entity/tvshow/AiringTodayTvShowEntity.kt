package com.elhady.movies.core.database.entity.tvshow

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AIRING_TODAY_TV_SHOWS_TABLE")
data class AiringTodayTvShowEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val imageUrl: String,
    val rate: Double,
    val title: String,
)
