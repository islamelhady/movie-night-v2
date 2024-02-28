package com.elhady.local.database.entity.movies

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NOW_PLAYING_MOVIE_TABLE")
data class NowPlayingMovieLocalDto(@PrimaryKey val id: Int, val name: String, val imageUrl: String)