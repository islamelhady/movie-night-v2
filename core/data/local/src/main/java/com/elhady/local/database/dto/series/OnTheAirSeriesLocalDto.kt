package com.elhady.local.database.dto.series

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ON_THE_AIR_SERIES_TABLE")
data class OnTheAirSeriesLocalDto(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String
)
