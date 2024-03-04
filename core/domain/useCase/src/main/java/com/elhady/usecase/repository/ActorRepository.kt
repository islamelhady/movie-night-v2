package com.elhady.usecase.repository

import androidx.paging.Pager
import com.elhady.entities.ActorEntity
import com.elhady.local.database.dto.actor.ActorLocalDto
import com.elhady.remote.response.actor.MovieCreditsDto
import com.elhady.remote.response.actor.PersonDto
import com.elhady.remote.response.dto.MovieRemoteDto

interface ActorRepository {

    suspend fun getPopularActorFromDatabase(): List<ActorEntity>
    suspend fun refreshPopularActor()

    fun getAllPopularPersons(): Pager<Int, PersonDto>

    suspend fun getPersonsDetails(actorID: Int): PersonDto?

    suspend fun getPersonMovies(actorID: Int): MovieCreditsDto?

    suspend fun getAllActorMovies(actorID: Int): Pager<Int, MovieRemoteDto>

    suspend fun searchForActors(query: String): Pager<Int, PersonDto>
}