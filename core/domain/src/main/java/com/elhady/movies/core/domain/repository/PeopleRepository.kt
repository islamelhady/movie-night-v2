package com.elhady.movies.core.domain.repository

import com.elhady.movies.core.domain.model.movie.Movie
import com.elhady.movies.core.domain.model.people.PeopleDetails
import com.elhady.movies.core.domain.model.people.People
import com.elhady.movies.core.domain.model.tvshow.TvShow

interface PeopleRepository {
    suspend fun getPopularPeopleFromDatabase(): List<People>
    suspend fun getPopularPeopleFromRemote(): List<People>
    suspend fun refreshPopularPeople()
    suspend fun getPersonDetails(personId: Int): PeopleDetails
    suspend fun getMoviesByPerson(personId: Int): List<Movie>
    suspend fun getTvShowsByPerson(personId: Int): List<TvShow>
}
