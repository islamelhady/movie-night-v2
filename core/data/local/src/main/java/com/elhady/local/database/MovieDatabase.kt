package com.elhady.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.elhady.local.Converters
import com.elhady.local.database.daos.ActorDao
import com.elhady.local.database.daos.MovieDao
import com.elhady.local.database.daos.SeriesDao
import com.elhady.local.database.entity.SearchHistoryEntity
import com.elhady.local.database.entity.WatchHistoryEntity
import com.elhady.local.database.entity.actor.ActorEntity
import com.elhady.local.database.entity.movies.AdventureMovieEntity
import com.elhady.local.database.entity.movies.MysteryMovieEntity
import com.elhady.local.database.entity.movies.NowPlayingMovieEntity
import com.elhady.local.database.entity.movies.PopularMovieEntity
import com.elhady.local.database.entity.movies.TopRatedMovieEntity
import com.elhady.local.database.entity.movies.TrendingMovieEntity
import com.elhady.local.database.entity.movies.UpcomingMovieEntity
import com.elhady.local.database.entity.series.AiringTodaySeriesEntity
import com.elhady.local.database.entity.series.OnTheAirSeriesEntity
import com.elhady.local.database.entity.series.TVSeriesListsEntity

@TypeConverters(Converters::class)
@Database(
    entities = [
        PopularMovieEntity::class,
        UpcomingMovieEntity::class,
        TrendingMovieEntity::class,
        NowPlayingMovieEntity::class,
        TopRatedMovieEntity::class,
        OnTheAirSeriesEntity::class,
        AiringTodaySeriesEntity::class,
        TVSeriesListsEntity::class,
        MysteryMovieEntity::class,
        AdventureMovieEntity::class,
        ActorEntity::class,
        SearchHistoryEntity::class,
        WatchHistoryEntity::class],
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun seriesDao(): SeriesDao
    abstract fun actorsDao(): ActorDao
}