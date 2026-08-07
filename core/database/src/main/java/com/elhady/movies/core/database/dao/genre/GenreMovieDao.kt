package com.elhady.movies.core.database.dao.genre

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elhady.movies.core.database.entity.genre.GenresMoviesEntity

@Dao
interface GenreMovieDao {

    @Query("select * from GENRES_MOVIES_TABLE")
    suspend fun getGenresMovies(): List<GenresMoviesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenresMovies(genresMovies: List<GenresMoviesEntity>)

    @Query("delete from GENRES_MOVIES_TABLE")
    suspend fun clearAllGenresMovies()
}