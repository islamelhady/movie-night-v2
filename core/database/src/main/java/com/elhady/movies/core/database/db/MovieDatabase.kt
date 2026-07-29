package com.elhady.movies.core.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.elhady.movies.core.database.converter.Converters
import com.elhady.movies.core.database.dao.GenreDao
import com.elhady.movies.core.database.dao.PeopleDao
import com.elhady.movies.core.database.dao.SearchDao
import com.elhady.movies.core.database.dao.WatchHistoryDao
import com.elhady.movies.core.database.dao.movie.NowPlayingMovieDao
import com.elhady.movies.core.database.dao.movie.PopularMovieDao
import com.elhady.movies.core.database.dao.movie.RecommendedMovieDao
import com.elhady.movies.core.database.dao.movie.TopRatedMovieDao
import com.elhady.movies.core.database.dao.movie.TrendingMovieDao
import com.elhady.movies.core.database.dao.movie.UpcomingMovieDao
import com.elhady.movies.core.database.dao.tv.AiringTodayTvShowDao
import com.elhady.movies.core.database.dao.tv.TvShowDao
import com.elhady.movies.core.database.entity.account.ProfileEntity
import com.elhady.movies.core.database.entity.genre.GenresMoviesEntity
import com.elhady.movies.core.database.entity.genre.GenresTvEntity
import com.elhady.movies.core.database.entity.movie.MovieEntity
import com.elhady.movies.core.database.entity.movie.MovieInWatchHistoryEntity
import com.elhady.movies.core.database.entity.movie.NowPlayingMovieEntity
import com.elhady.movies.core.database.entity.movie.PopularMovieEntity
import com.elhady.movies.core.database.entity.movie.RecommendedMovieEntity
import com.elhady.movies.core.database.entity.movie.TopRatedMovieEntity
import com.elhady.movies.core.database.entity.movie.TrendingMoviesEntity
import com.elhady.movies.core.database.entity.movie.UpcomingMovieEntity
import com.elhady.movies.core.database.entity.people.PopularPeopleEntity
import com.elhady.movies.core.database.entity.search.SearchHistoryEntity
import com.elhady.movies.core.database.entity.tvshow.AiringTodayTvShowEntity
import com.elhady.movies.core.database.entity.tvshow.TvShowEntity
import com.elhady.movies.core.database.entity.user.UserEntity

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
abstract class MovieDatabase : RoomDatabase() {
    abstract val tvShowDao: TvShowDao
    abstract val airingTodayTvShowDao: AiringTodayTvShowDao

    abstract val searchDao: SearchDao
    abstract val peopleDao: PeopleDao

    abstract val genreDao: GenreDao
    abstract val watchHistoryDao: WatchHistoryDao
    abstract val nowPlayingMovieDao: NowPlayingMovieDao
    abstract val popularMovieDao: PopularMovieDao
    abstract val recommendedMovieDao: RecommendedMovieDao
    abstract val upcomingMovieDao: UpcomingMovieDao
    abstract val trendingMovieDao: TrendingMovieDao
    abstract val topRatedMovieDao: TopRatedMovieDao

}