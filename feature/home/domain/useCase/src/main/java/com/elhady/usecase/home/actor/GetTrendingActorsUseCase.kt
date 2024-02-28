package com.elhady.usecase.home.actor

import com.elhady.entities.ActorEntity
import com.elhady.usecase.repository.ActorRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTrendingActorsUseCase @Inject constructor(
    private val repository: ActorRepository,
    private val actorMapper: ActorsMapper
) {
    suspend operator fun invoke(): List<ActorEntity> {
        return repository.getPopularPersons().map {
            actorMapper.map(it)
        }
    }
}