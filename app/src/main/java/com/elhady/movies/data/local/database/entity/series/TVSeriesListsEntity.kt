package com.elhady.movies.data.local.database.entity.series

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TV_SERIES_TABLE")
data class TVSeriesListsEntity(@PrimaryKey val id: Int, val name: String, val imageUrl: String)