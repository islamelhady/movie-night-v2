package com.elhady.movies.core.domain.repository

import com.elhady.movies.core.domain.model.movie.MovieEntity
import com.elhady.movies.core.domain.model.people.PeopleEntity
import com.elhady.movies.core.domain.model.tvshow.TvEntity

interface SearchRepository {
    suspend fun getSearchHistory(keyword: String): List<String>
    suspend fun searchHistory(): List<String>
    suspend fun insertSearchHistory(keyword: String)
    suspend fun clearAllSearchHistory()
    suspend fun deleteSearchHistory(keyword: String)

    suspend fun searchForMovies(keyword: String): List<MovieEntity>
    suspend fun searchForTv(keyword: String): List<TvEntity>
    suspend fun searchForPeople(keyword: String): List<PeopleEntity>
}
