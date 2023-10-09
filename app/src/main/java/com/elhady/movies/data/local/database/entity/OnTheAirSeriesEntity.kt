package com.elhady.movies.data.local.database.entity

import androidx.room.Entity

@Entity(tableName = "ON_THE_AIR_SERIES_TABLE")
data class OnTheAirSeriesEntity(val id: Int, val name: String, val imageUrl: String)
