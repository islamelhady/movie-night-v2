package com.elhady.movies.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.elhady.movies.data.database.daos.MovieDao
import com.elhady.movies.data.database.entity.PopularMovieEntity

@Database(entities = [PopularMovieEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}