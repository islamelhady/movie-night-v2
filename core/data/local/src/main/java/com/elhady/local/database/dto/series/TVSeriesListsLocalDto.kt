package com.elhady.local.database.dto.series

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TV_SERIES_TABLE")
data class TVSeriesListsLocalDto(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String
)