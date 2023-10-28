package com.elhady.movies.data.repository

import androidx.paging.Pager
import com.elhady.movies.data.local.database.entity.actor.ActorEntity
import com.elhady.movies.data.remote.response.MovieDto
import com.elhady.movies.data.remote.response.PersonDto
import com.elhady.movies.data.remote.response.actor.MovieCreditsDto
import kotlinx.coroutines.flow.Flow

interface ActorRepository {

    suspend fun getPopularPersons(): Flow<List<ActorEntity>>

    fun getAllPopularPersons(): Pager<Int, PersonDto>

    suspend fun getPersonsDetails(actorID: Int): PersonDto?

    suspend fun getPersonMovies(actorID: Int): MovieCreditsDto?

    fun getAllActorMovies(actorID: Int): Pager<Int, MovieDto>
}