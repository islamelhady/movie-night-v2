package com.elhady.movies.core.data.local.database.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PROFILE_TABLE")
data class ProfileLocalDto(
    @PrimaryKey(autoGenerate = false)
    val username:String,
    val avatarUrl:String
)

