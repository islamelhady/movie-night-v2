package com.elhady.movies.core.domain.repository

import com.elhady.movies.core.domain.model.GenreEntity

interface GenreRepository {
    suspend fun getGenresMovies(): List<GenreEntity>
    suspend fun refreshGenres()
    suspend fun getGenresTvs(): List<GenreEntity>
    suspend fun refreshGenresTv()
}
