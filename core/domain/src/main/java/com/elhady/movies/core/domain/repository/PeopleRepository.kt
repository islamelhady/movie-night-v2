package com.elhady.movies.core.domain.repository

import com.elhady.movies.core.domain.model.MovieEntity
import com.elhady.movies.core.domain.model.PeopleDetailsEntity
import com.elhady.movies.core.domain.model.PeopleEntity
import com.elhady.movies.core.domain.model.TvShowEntity

interface PeopleRepository {
    suspend fun getPopularPeopleFromDatabase(): List<PeopleEntity>
    suspend fun getPopularPeopleFromRemote(): List<PeopleEntity>
    suspend fun refreshPopularPeople()
    suspend fun getPersonDetails(personId: Int): PeopleDetailsEntity
    suspend fun getMoviesByPerson(personId: Int): List<MovieEntity>
    suspend fun getTvShowsByPerson(personId: Int): List<TvShowEntity>
}
