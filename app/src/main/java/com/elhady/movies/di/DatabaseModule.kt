package com.elhady.movies.di

import android.content.Context
import androidx.room.Room
import com.elhady.local.DataStorePreferences
import com.elhady.local.database.Converters
import com.elhady.local.database.MovieDatabase
import com.elhady.local.database.dao.ActorDao
import com.elhady.local.database.dao.MovieDao
import com.elhady.local.database.dao.SeriesDao
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context, converters: Converters): MovieDatabase {
        return Room.databaseBuilder(context, MovieDatabase::class.java, "MovieDatabase")
            .addTypeConverter(converters)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverter(gson: Gson): Converters {
        return Converters(gson)
    }

    @Singleton
    @Provides
    fun provideMovieDao(movieDatabase: MovieDatabase): MovieDao {
        return movieDatabase.movieDao()
    }

    @Singleton
    @Provides
    fun provideSeriesDao(movieDatabase: MovieDatabase): SeriesDao {
        return movieDatabase.seriesDao()
    }

    @Singleton
    @Provides
    fun provideActorDao(movieDatabase: MovieDatabase): ActorDao {
        return movieDatabase.actorsDao()
    }

    @Provides
    @Singleton
    fun provideDataStorePreferences(@ApplicationContext context: Context) = DataStorePreferences(context)
}