package com.elhady.movies.domain.usecases.home.actor

import com.elhady.movies.data.repository.ActorRepository
import com.elhady.movies.domain.mappers.actor.ActorsMapper
import com.elhady.movies.domain.models.Actor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTrendingActorsUseCase(
    private val repository: ActorRepository,
    private val actorMapper: ActorsMapper
) {
    suspend operator fun invoke(): Flow<List<Actor>> {
        return repository.getTrendingActors().map { list ->
            list.map {
                actorMapper.map(it)
            }
        }
    }
}