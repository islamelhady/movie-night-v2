package com.elhady.movies.domain.usecases.home.actor

import com.elhady.movies.data.repository.ActorRepository
import com.elhady.movies.domain.mappers.actor.ActorsMapper
import com.elhady.movies.domain.models.Actor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTrendingActorsUseCase @Inject constructor(
    private val repository: ActorRepository,
    private val actorMapper: ActorsMapper
) {
    suspend operator fun invoke(): List<Actor> {
        return repository.getPopularPersons().map {
            actorMapper.map(it)
        }
    }
}