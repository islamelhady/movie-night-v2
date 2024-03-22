package com.elhady.movies.core.data.local.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.elhady.movies.core.data.local.database.dto.GenresMoviesLocalDto
import com.elhady.movies.core.data.local.database.dto.GenresTvsLocalDto
import com.elhady.movies.core.data.local.database.dto.PopularPeopleLocalDto
import com.elhady.movies.core.data.local.database.dto.ProfileLocalDto
import com.elhady.movies.core.data.local.database.dto.SearchHistoryLocalDto
import com.elhady.movies.core.data.local.database.dto.UserLocalDto
import com.elhady.movies.core.data.local.database.dto.movie.MovieInWatchHistoryLocalDto
import com.elhady.movies.core.data.local.database.dto.movie.MovieLocalDto
import com.elhady.movies.core.data.local.database.dto.movie.NowPlayingMovieLocalDto
import com.elhady.movies.core.data.local.database.dto.movie.PopularMovieLocalDto
import com.elhady.movies.core.data.local.database.dto.movie.RecommendedMovieLocalDto
import com.elhady.movies.core.data.local.database.dto.movie.TopRatedMovieLocalDto
import com.elhady.movies.core.data.local.database.dto.movie.TrendingMoviesLocalDto
import com.elhady.movies.core.data.local.database.dto.movie.UpcomingMovieLocalDto
import com.elhady.movies.core.data.local.database.dto.tvshow.AiringTodayTvShowsLocalDto
import com.elhady.movies.core.data.local.database.dto.tvshow.TvShowsLocalDto


@Database(
    entities = [
        PopularMovieLocalDto::class,
        TopRatedMovieLocalDto::class,
        UpcomingMovieLocalDto::class,
        NowPlayingMovieLocalDto::class,
        RecommendedMovieLocalDto::class,
        TrendingMoviesLocalDto::class,
        PopularPeopleLocalDto::class,
        SearchHistoryLocalDto::class,
        GenresMoviesLocalDto::class,
        ProfileLocalDto::class,
        GenresTvsLocalDto::class,
        MovieLocalDto::class,
        UserLocalDto::class,
        TvShowsLocalDto::class,
        MovieInWatchHistoryLocalDto::class,
        AiringTodayTvShowsLocalDto::class
    ],
    version = 6,
    exportSchema = true,
//    autoMigrations = [AutoMigration(from = 5, to = 6)]
)
@TypeConverters(Convertors::class)
abstract class MovieDataBase : RoomDatabase() {
    abstract val movieDao: MovieDao
    abstract val tvShowDao: TvShowDao
}