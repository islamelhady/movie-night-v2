package com.elhady.movies.core.domain.repository

import com.elhady.movies.core.domain.model.common.Genre

interface GenreRepository {
    suspend fun getGenresMovies(): List<Genre>
    suspend fun refreshGenres()
    suspend fun getGenresTvs(): List<Genre>
    suspend fun refreshGenresTv()
}
