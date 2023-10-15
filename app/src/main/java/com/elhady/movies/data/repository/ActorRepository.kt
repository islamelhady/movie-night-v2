package com.elhady.movies.data.repository

import com.elhady.movies.data.local.database.entity.actor.ActorEntity
import kotlinx.coroutines.flow.Flow

interface ActorRepository {

    suspend fun getTrendingActors(): Flow<List<ActorEntity>>
}