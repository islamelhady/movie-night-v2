package com.elhady.local.database.entity.series

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AIRING_TODAY_SERIES_TABLE")
data class AiringTodaySeriesLocalDto(@PrimaryKey val id: Int, val name: String, val imageUrl: String)
