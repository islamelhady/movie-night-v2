package com.elhady.movies.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.elhady.movies.data.local.database.daos.MovieDao
import com.elhady.movies.data.local.database.entity.PopularMovieEntity
import com.elhady.movies.data.local.Converters
import com.elhady.movies.data.local.database.entity.TrendingMovieEntity

@TypeConverters(Converters::class)
@Database(entities = [PopularMovieEntity::class, TrendingMovieEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}