package com.elhady.movies.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.elhady.movies.data.local.database.daos.MovieDao
import com.elhady.movies.data.local.database.entity.movies.PopularMovieEntity
import com.elhady.movies.data.local.database.entity.movies.UpcomingMovieEntity
import com.elhady.movies.data.local.database.entity.movies.TrendingMovieEntity
import com.elhady.movies.data.local.Converters
import com.elhady.movies.data.local.database.daos.SeriesDao
import com.elhady.movies.data.local.database.entity.movies.AdventureMovieEntity
import com.elhady.movies.data.local.database.entity.movies.MysteryMovieEntity
import com.elhady.movies.data.local.database.entity.movies.NowPlayingMovieEntity
import com.elhady.movies.data.local.database.entity.series.OnTheAirSeriesEntity
import com.elhady.movies.data.local.database.entity.movies.TopRatedMovieEntity
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