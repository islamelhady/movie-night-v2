package com.elhady.movies.di

import android.content.Context
import androidx.room.Room
import com.elhady.movies.core.data.local.database.Convertors
import com.elhady.movies.core.data.local.database.MovieDao
import com.elhady.movies.core.data.local.database.MovieDataBase
import com.elhady.movies.core.data.local.database.TvShowDao
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
        convertors: Convertors
    ): MovieDataBase {
        return Room.databaseBuilder(
            context,
            MovieDataBase::class.java,
            "MovieDatabase.db"
        ).addTypeConverter(convertors).build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(movieDataBase: MovieDataBase): MovieDao {
        return movieDataBase.movieDao
    }

    @Singleton
    @Provides
    fun provideTvShowDao(movieDataBase: MovieDataBase): TvShowDao {
        return movieDataBase.tvShowDao
    }

    @Singleton
    @Provides
    fun provideConverters(gson: Gson): Convertors {
        return Convertors(gson)
    }

    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

}