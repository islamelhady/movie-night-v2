package com.elhady.movies.core.data.local.database.dto.tvshow

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TV_SHOWS_TABLE")
data class TvShowsLocalDto(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val imageUrl: String,
    val rate: Double,
    val title: String,
)
