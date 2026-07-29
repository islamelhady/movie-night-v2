package com.elhady.movies.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elhady.movies.core.database.entity.genre.GenresMoviesEntity
import com.elhady.movies.core.database.entity.genre.GenresTvEntity

@Dao
interface GenreDao {

    @Query("select * from GENRES_MOVIES_TABLE")
    suspend fun getGenresMovies(): List<GenresMoviesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenresMovies(genresMovies: List<GenresMoviesEntity>)

    @Query("delete from GENRES_MOVIES_TABLE")
    suspend fun clearAllGenresMovies()

    @Query("select * from GENRES_TVS_TABLE")
    suspend fun getGenresTvs(): List<GenresTvEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenresTvs(genresMovies: List<GenresTvEntity>)

    @Query("delete from GENRES_TVS_TABLE")
    suspend fun clearAllGenresTvs()
}