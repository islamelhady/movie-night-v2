package com.elhady.movies.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.elhady.movies.data.database.daos.MovieDao
import com.elhady.movies.data.database.entity.PopularMovieEntity

@TypeConverters(Converters::class)
@Database(entities = [PopularMovieEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}