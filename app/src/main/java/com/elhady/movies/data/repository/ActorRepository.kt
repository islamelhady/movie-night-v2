package com.elhady.movies.data.repository

import androidx.paging.Pager
import com.elhady.movies.data.local.database.entity.actor.ActorEntity
import com.elhady.movies.data.remote.response.movie.MovieDto
import com.elhady.movies.data.remote.response.actor.PersonDto
import com.elhady.movies.data.remote.response.actor.MovieCreditsDto
import kotlinx.coroutines.flow.Flow

interface ActorRepository {

    suspend fun getPopularPersons(): List<ActorEntity>

    fun getAllPopularPersons(): Pager<Int, PersonDto>

    suspend fun getPersonsDetails(actorID: Int): PersonDto?

    suspend fun getPersonMovies(actorID: Int): MovieCreditsDto?

    suspend fun getAllActorMovies(actorID: Int): Pager<Int, MovieDto>

    suspend fun searchForActors(query: String): Pager<Int, PersonDto>
}