package com.elhady.movies.core.database.dao.genre

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elhady.movies.core.database.entity.genre.GenresTvEntity

@Dao
interface GenreTvDao {

    @Query("select * from GENRES_TVS_TABLE")
    suspend fun getGenresTvs(): List<GenresTvEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenresTvs(genresMovies: List<GenresTvEntity>)

    @Query("delete from GENRES_TVS_TABLE")
    suspend fun clearAllGenresTvs()
}