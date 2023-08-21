package com.elhady.movies.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.elhady.movies.data.database.entity.Saved
import com.elhady.movies.data.database.entity.SearchHistory

@Database(entities = [SearchHistory::class, Saved::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun savedDao(): SavedDao

    companion object {

        private const val DATABASE_NAME = "MovieDatabase"

        @Volatile
        private var instance: MovieDatabase? = null
        fun getInstance(context: Context): MovieDatabase {
            return instance ?: synchronized(this) {
                buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): MovieDatabase {
            return Room.databaseBuilder(context, MovieDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}