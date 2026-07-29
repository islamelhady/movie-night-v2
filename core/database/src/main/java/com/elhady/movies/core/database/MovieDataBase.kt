package com.elhady.movies.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.elhady.movies.core.database.entity.GenresMoviesEntity
import com.elhady.movies.core.database.entity.GenresTvEntity
import com.elhady.movies.core.database.entity.PopularPeopleEntity
import com.elhady.movies.core.database.entity.ProfileEntity
import com.elhady.movies.core.database.entity.SearchHistoryEntity
import com.elhady.movies.core.database.entity.UserEntity
import com.elhady.movies.core.database.entity.movie.MovieInWatchHistoryEntity
import com.elhady.movies.core.database.entity.movie.MovieEntity
import com.elhady.movies.core.database.entity.movie.NowPlayingMovieEntity
import com.elhady.movies.core.database.entity.movie.PopularMovieEntity
import com.elhady.movies.core.database.entity.movie.RecommendedMovieEntity
import com.elhady.movies.core.database.entity.movie.TopRatedMovieEntity
import com.elhady.movies.core.database.entity.movie.TrendingMoviesEntity
import com.elhady.movies.core.database.entity.movie.UpcomingMovieEntity
import com.elhady.movies.core.database.entity.tvshow.AiringTodayTvShowEntity
import com.elhady.movies.core.database.entity.tvshow.TvShowEntity


@Database(
    entities = [
        PopularMovieEntity::class,
        TopRatedMovieEntity::class,
        UpcomingMovieEntity::class,
        NowPlayingMovieEntity::class,
        RecommendedMovieEntity::class,
        TrendingMoviesEntity::class,
        PopularPeopleEntity::class,
        SearchHistoryEntity::class,
        GenresMoviesEntity::class,
        ProfileEntity::class,
        GenresTvEntity::class,
        MovieEntity::class,
        UserEntity::class,
        TvShowEntity::class,
        MovieInWatchHistoryEntity::class,
        AiringTodayTvShowEntity::class
    ],
    version = 9,
    exportSchema = true,
//    autoMigrations = [AutoMigration(from = 8, to = 9)]
)
@TypeConverters(Converters::class)
abstract class MovieDataBase : RoomDatabase() {
    abstract val movieDao: MovieDao
    abstract val tvShowDao: TvShowDao
}
