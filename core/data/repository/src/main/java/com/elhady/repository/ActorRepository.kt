package com.elhady.repository

import androidx.paging.Pager
import com.elhady.local.database.entity.actor.ActorLocalDto
import com.elhady.remote.response.actor.PersonDto
import com.elhady.remote.response.dto.MovieDto

interface ActorRepository {

    suspend fun getPopularPersons(): List<ActorLocalDto>

    fun getAllPopularPersons(): Pager<Int, PersonDto>

//    suspend fun getPersonsDetails(actorID: Int): PersonDto?

    suspend fun getPersonMovies(actorID: Int): MovieCreditsDto?

    suspend fun getAllActorMovies(actorID: Int): Pager<Int, MovieDto>

    suspend fun searchForActors(query: String): Pager<Int, PersonDto>
}