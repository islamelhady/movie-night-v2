package com.elhady.movies.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PROFILE_TABLE")
data class ProfileEntity(
    @PrimaryKey(autoGenerate = false)
    val username:String,
    val avatarUrl:String
)
