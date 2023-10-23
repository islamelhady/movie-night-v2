package com.elhady.movies.data.repository

import androidx.paging.Pager
import androidx.paging.PagingSource
import com.elhady.movies.data.local.database.entity.actor.ActorEntity
import com.elhady.movies.data.remote.response.PersonDto
import kotlinx.coroutines.flow.Flow

interface ActorRepository {

    suspend fun getTrendingActors(): Flow<List<ActorEntity>>
    suspend fun getAllActors(): Pager<Int, PersonDto>

    suspend fun getActorDetails(actorID: Int): PersonDto?
}