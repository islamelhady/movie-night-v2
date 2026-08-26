package com.elhady.movies.core.domain.repository

import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.core.domain.model.people.People
import com.elhady.movies.core.domain.model.tvshow.Tv

interface SearchRepository {
    suspend fun getSearchHistory(keyword: String): List<String>
    suspend fun searchHistory(): List<String>
    suspend fun insertSearchHistory(keyword: String)
    suspend fun clearAllSearchHistory()
    suspend fun deleteSearchHistory(keyword: String)

    suspend fun searchForMovies(keyword: String): List<Movie>
    suspend fun searchForTv(keyword: String): List<Tv>
    suspend fun searchForPeople(keyword: String): List<People>
}
