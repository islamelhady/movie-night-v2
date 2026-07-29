package com.elhady.movies.core.database.di

import android.content.Context
import androidx.room.Room
import com.elhady.movies.core.database.converter.Converters
import com.elhady.movies.core.database.dao.MovieDao
import com.elhady.movies.core.database.db.MovieDatabase
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
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun providesMovieDatabase(
        @ApplicationContext context: Context,
        converters: Converters
    ): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "MovieDatabase.db"
        ).addTypeConverter(converters).build()
    }

    // region movie

    @Singleton
    @Provides
    fun provideNowPlayingMovieDao(movieDataBase: MovieDatabase): NowPlayingMovieDao {
        return movieDataBase.nowPlayingMovieDao
    }

    @Singleton
    @Provides
    fun providePopularMovieDao(movieDataBase: MovieDatabase): PopularMovieDao {
        return movieDataBase.popularMovieDao
    }

    @Singleton
    @Provides
    fun provideRecommendedMovieDao(movieDataBase: MovieDatabase): RecommendedMovieDao {
        return movieDataBase.recommendedMovieDao
    }

    @Singleton
    @Provides
    fun provideTopRatedMovieDao(movieDataBase: MovieDatabase): TopRatedMovieDao {
        return movieDataBase.topRatedMovieDao
    }

    @Singleton
    @Provides
    fun provideTrendingMovieDao(movieDataBase: MovieDatabase): TrendingMovieDao {
        return movieDataBase.trendingMovieDao
    }

    @Singleton
    @Provides
    fun provideUpcomingMovieDao(movieDataBase: MovieDatabase): UpcomingMovieDao {
        return movieDataBase.upcomingMovieDao
    }

    // endregion

    // region tv show
    @Singleton
    @Provides
    fun provideTvShowDao(movieDataBase: MovieDatabase): TvShowDao {
        return movieDataBase.tvShowDao
    }

    @Singleton
    @Provides
    fun provideAiringTodayTvShowDao(movieDataBase: MovieDatabase): AiringTodayTvShowDao {
        return movieDataBase.airingTodayTvShowDao
    }

    // endregion

    @Singleton
    @Provides
    fun provideGenreDao(movieDataBase: MovieDatabase): GenreDao {
        return movieDataBase.genreDao
    }

    @Singleton
    @Provides
    fun providePeopleDao(movieDataBase: MovieDatabase): PeopleDao {
        return movieDataBase.peopleDao
    }

    @Singleton
    @Provides
    fun provideSearchDao(movieDataBase: MovieDatabase): SearchDao {
        return movieDataBase.searchDao
    }

    @Singleton
    @Provides
    fun provideWatchHistoryDao(movieDataBase: MovieDatabase): WatchHistoryDao {
        return movieDataBase.watchHistoryDao
    }

    @Singleton
    @Provides
    fun provideConverters(gson: Gson): Converters {
        return Converters(gson)
    }

    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

}
