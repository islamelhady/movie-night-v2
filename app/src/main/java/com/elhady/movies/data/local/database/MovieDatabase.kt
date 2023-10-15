package com.elhady.movies.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.elhady.movies.data.local.database.daos.MovieDao
import com.elhady.movies.data.local.database.entity.PopularMovieEntity
import com.elhady.movies.data.local.database.entity.UpcomingMovieEntity
import com.elhady.movies.data.local.database.entity.TrendingMovieEntity
import com.elhady.movies.data.local.Converters
import com.elhady.movies.data.local.database.daos.SeriesDao
import com.elhady.movies.data.local.database.entity.AdventureMovieEntity
import com.elhady.movies.data.local.database.entity.MysteryMovieEntity
import com.elhady.movies.data.local.database.entity.NowPlayingMovieEntity
import com.elhady.movies.data.local.database.entity.series.OnTheAirSeriesEntity
import com.elhady.movies.data.local.database.entity.TopRatedMovieEntity
import com.elhady.movies.data.local.database.entity.series.AiringTodaySeriesEntity
import com.elhady.movies.data.local.database.entity.series.TVSeriesListsEntity

@TypeConverters(Converters::class)
@Database(
    entities = [PopularMovieEntity::class, UpcomingMovieEntity::class, TrendingMovieEntity::class, NowPlayingMovieEntity::class, TopRatedMovieEntity::class, OnTheAirSeriesEntity::class, AiringTodaySeriesEntity::class, TVSeriesListsEntity::class, MysteryMovieEntity::class, AdventureMovieEntity::class],
    version = 1
)

abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun seriesDao(): SeriesDao
}