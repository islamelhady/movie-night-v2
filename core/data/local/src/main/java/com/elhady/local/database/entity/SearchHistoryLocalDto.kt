package com.elhady.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SEARCH_HISTORY_TABLE")
data class SearchHistoryLocalDto(@PrimaryKey(autoGenerate = false) val keyword: String)
