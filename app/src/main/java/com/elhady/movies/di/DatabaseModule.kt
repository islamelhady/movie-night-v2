package com.elhady.movies.di

import android.content.Context
import androidx.room.Room
import com.elhady.movies.data.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private lateinit var movieDatabase: MovieDatabase

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(context, MovieDatabase::class.java, "MovieDatabase").build()
    }
}