package com.elhady.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.elhady.local.database.dao.ActorDao
import com.elhady.local.database.dao.MovieDao
import com.elhady.local.database.dao.SeriesDao
import com.elhady.local.database.dto.SearchHistoryLocalDto
import com.elhady.local.database.dto.WatchHistoryLocalDto
import com.elhady.local.database.dto.actor.ActorLocalDto
import com.elhady.local.database.dto.movies.AdventureMovieLocalDto
import com.elhady.local.database.dto.movies.MysteryMovieLocalDto
import com.elhady.local.database.dto.movies.NowPlayingMovieLocalDto
import com.elhady.local.database.dto.movies.PopularMovieLocalDto
import com.elhady.local.database.dto.movies.TopRatedMovieLocalDto
import com.elhady.local.database.dto.movies.TrendingMovieLocalDto
import com.elhady.local.database.dto.movies.UpcomingMovieLocalDto
import com.elhady.local.database.dto.series.AiringTodaySeriesLocalDto
import com.elhady.local.database.dto.series.OnTheAirSeriesLocalDto
import com.elhady.local.database.dto.series.TVSeriesListsLocalDto

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
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun seriesDao(): SeriesDao
    abstract fun actorsDao(): ActorDao
}