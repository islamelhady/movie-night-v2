package com.elhady.movies.core.domain.repository

import com.elhady.movies.core.domain.model.movie.MovieEntity
import com.elhady.movies.core.domain.model.people.PeopleDetailsEntity
import com.elhady.movies.core.domain.model.people.PeopleEntity
import com.elhady.movies.core.domain.model.tvshow.TvShowEntity

interface PeopleRepository {
    suspend fun getPopularPeopleFromDatabase(): List<PeopleEntity>
    suspend fun getPopularPeopleFromRemote(): List<PeopleEntity>
    suspend fun refreshPopularPeople()
    suspend fun getPersonDetails(personId: Int): PeopleDetailsEntity
    suspend fun getMoviesByPerson(personId: Int): List<MovieEntity>
    suspend fun getTvShowsByPerson(personId: Int): List<TvShowEntity>
}
