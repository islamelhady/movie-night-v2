package com.elhady.movies.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.elhady.movies.data.local.database.daos.MovieDao
import com.elhady.movies.data.local.database.entity.PopularMovieEntity
import com.elhady.movies.data.local.database.entity.UpcomingMovieEntity
import com.elhady.movies.data.local.database.entity.TrendingMovieEntity
import com.elhady.movies.data.local.Converters

@TypeConverters(Converters::class)
@Database(entities = [PopularMovieEntity::class, UpcomingMovieEntity::class, TrendingMovieEntity::class], version = 1)

abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}