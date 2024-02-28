package com.elhady.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.elhady.local.database.daos.ActorDao
import com.elhady.local.database.daos.MovieDao
import com.elhady.local.database.daos.SeriesDao
import com.elhady.local.database.entity.SearchHistoryLocalDto
import com.elhady.local.database.entity.WatchHistoryLocalDto
import com.elhady.local.database.entity.actor.ActorLocalDto
import com.elhady.local.database.entity.movies.AdventureMovieLocalDto
import com.elhady.local.database.entity.movies.MysteryMovieLocalDto
import com.elhady.local.database.entity.movies.NowPlayingMovieLocalDto
import com.elhady.local.database.entity.movies.PopularMovieLocalDto
import com.elhady.local.database.entity.movies.TopRatedMovieLocalDto
import com.elhady.local.database.entity.movies.TrendingMovieLocalDto
import com.elhady.local.database.entity.movies.UpcomingMovieLocalDto
import com.elhady.local.database.entity.series.AiringTodaySeriesLocalDto
import com.elhady.local.database.entity.series.OnTheAirSeriesLocalDto
import com.elhady.local.database.entity.series.TVSeriesListsLocalDto

@TypeConverters(Converters::class)
@Database(
    entities = [
        PopularMovieLocalDto::class,
        UpcomingMovieLocalDto::class,
        TrendingMovieLocalDto::class,
        NowPlayingMovieLocalDto::class,
        TopRatedMovieLocalDto::class,
        OnTheAirSeriesLocalDto::class,
        AiringTodaySeriesLocalDto::class,
        TVSeriesListsLocalDto::class,
        MysteryMovieLocalDto::class,
        AdventureMovieLocalDto::class,
        ActorLocalDto::class,
        SearchHistoryLocalDto::class,
        WatchHistoryLocalDto::class],
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun seriesDao(): SeriesDao
    abstract fun actorsDao(): ActorDao
}